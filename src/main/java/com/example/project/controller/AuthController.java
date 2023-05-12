package com.example.project.controller;

import com.example.project.Security.Exception.TokenRefreshException;
import com.example.project.entity.*;
import com.example.project.Payload.Request.*;
import com.example.project.entity.response.JwtResponse;
import com.example.project.entity.response.MessageResponse;
import com.example.project.entity.response.TokenRefreshResponse;
import com.example.project.repository.LocationRepository;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;
import com.example.project.Security.Jwt.JwtUtils;
import com.example.project.service.LocationService;
import com.example.project.service.impl.BlacklistService;
import com.example.project.service.impl.RefreshTokenService;
import com.example.project.service.impl.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BlacklistService blacklistService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    LocationService locationService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;


    @PostMapping("/user/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserSignUp userSignUp) throws Exception {
        try {
            logger.info("Executing registerUser Endpoint",
                    "method", "POST",
                    "path", "/user/signup",
                    "status", HttpStatus.OK.value()
            );

            logger.debug("Checking if the username already exists");
            if (userRepository.existsUserByUsername(userSignUp.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username is already taken!"));
            }

            logger.debug("Checking if the Email already exists");
            if (userRepository.existsUsersByEmail(userSignUp.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email already exists"));
            }

            logger.debug("Creating new user object");
            // Create new user's account
            User user = new User(userSignUp.getUsername(), encoder.encode(userSignUp.getPassword()), userSignUp.getEmail());

            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            user.setRoles(roles);

            Location location = locationService.getLocation(userSignUp.getLocationId());
            user.setLocation(location);

            logger.debug("Persisting the new User details into database");
            userRepository.save(user);

            logger.info("User Registered Successfully");
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }
        catch(Exception e){
            logger.error("Error while Registering User","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

    @PostMapping("/user/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody UserLogin userLogin) throws Exception {
        try {
            logger.info("Executing authenticateUser Endpoint",
                    "method", "POST",
                    "path", "/user/signin",
                    "status", HttpStatus.OK.value()
            );
            //System.out.println(userLogin.getUsername()+userLogin.getPassword());
            logger.debug("Authenticating username and password");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Generating JWT token");
            String jwt = jwtUtils.generateJwtToken(authentication);

            logger.debug("Authorizing User role");
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            logger.debug("Creating refresh Token");
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

            logger.debug("Returning Response which contains auth token");
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles,
                    refreshToken.getToken()));
        }
        catch(Exception e){
            logger.error("Error Executing Authenticating User","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

    @PostMapping("/user/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) throws Exception {
        try {
            logger.info("Executing refreshtoken Endpoint",
                    "method", "POST",
                    "path", "/user/refreshtoken",
                    "status", HttpStatus.OK.value()
            );
            String requestRefreshToken = request.getRefreshToken();

            logger.debug("Generating new JWT using the refreshToken");
            return refreshTokenService.findByToken(requestRefreshToken)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(user -> {
                        String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                        return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                    })
                    .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
        }
        catch(Exception e){
            logger.error("Error Executing JWT generation using RefreshToken","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

    @GetMapping("/user/signout/{user_id}")
    @PreAuthorize("hasRole('USER')")
    public void signout(@RequestHeader(value="Authorization") String token, @PathVariable Integer user_id) throws Exception {
        try {
            logger.info("Executing signout Endpoint",
                    "method", "GET",
                    "path", "/user/signout/{user_id}",
                    "status", HttpStatus.OK.value()
            );
            logger.debug("Blacklisting JWT token and deleting refresh token");
            Blacklist blacklist = new Blacklist(token);
            blacklistService.setToken(blacklist);
            blacklistService.deleteRefreshTokenUser(user_id);
        }
        catch(Exception e){
            logger.error("Error while Signing Out","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

}
