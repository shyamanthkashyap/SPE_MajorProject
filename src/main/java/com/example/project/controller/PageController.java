package com.example.project.controller;

import com.example.project.entity.User;
import com.example.project.entity.response.ResponseEntity;
import com.example.project.model.UserRegisterFactory;
import com.example.project.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Null;

@Controller
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PageController {

    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserRegisterFactory userRegisterFactory;

    @GetMapping(value = "/userProfile/{id}")
    @ResponseBody
    public ResponseEntity<User> userProfile(@PathVariable Integer id) throws Exception {
        try {
            logger.info("Executing userProfile Endpoint",
                    "method", "GET",
                    "path", "/userProfile/{id}",
                    "status", HttpStatus.OK.value()
            );
            User user = userService.findById(id);
            return new ResponseEntity<>(HttpStatus.OK.value(), "Find user profile success", user);
        }
        catch(Exception e){
            logger.error("Error Executing userProfile","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

    @PostMapping(value = "/updateProfile/{id}")
    @ResponseBody
    public ResponseEntity<User> updateProfile(@RequestBody @Valid String profile, @PathVariable Integer id) throws Exception{
        try {
            logger.info("Executing updateProfile Endpoint",
                    "method", "POST",
                    "path", "/updateProfile/{id}",
                    "status", HttpStatus.OK.value()
            );
            User user = userService.findById(id);
            user.setProfile(profile);
            user = userService.updateProfile(user);
            return new ResponseEntity<>(HttpStatus.OK.value(), "update profile success", user);
        }
        catch (Exception e){
            logger.error("Error Executing updateProfile","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new NullPointerException();
        }
    }

}
