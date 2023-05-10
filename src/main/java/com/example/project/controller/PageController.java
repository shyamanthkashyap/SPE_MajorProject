package com.example.project.controller;

import com.example.project.entity.User;
import com.example.project.entity.response.ResponseEntity;
import com.example.project.model.UserRegisterFactory;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PageController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRegisterFactory userRegisterFactory;

//    @PostMapping(value = "/login")
//    @ResponseBody
//    public ResponseEntity<User> login(@RequestBody @Valid User user) {
//        User userLogin = userService.login(user.getUserId(), user.getPwd());
//        if(userLogin!=null){
//            return new ResponseEntity<>(HttpStatus.OK.value(), "login success", userLogin);
//        }
//        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"No such user or the account sign-in was incorrect");
//    }

//    @PostMapping(value = "/register")
//    @ResponseBody
//    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterFactory.UserRegister userRegister){
//        User user = userRegisterFactory.rpoToPojo.apply(userRegister);
//        user = userService.register(user);
//        return new ResponseEntity<>(HttpStatus.OK.value(), "Registration success", user);
//    }

//    @GetMapping(value = "/signOut")
//    public ResponseEntity<Void> signOut(){
//        return new ResponseEntity<>(HttpStatus.OK.value(),"Sign out success");
//    }

    @GetMapping(value = "/userProfile/{id}")
    @ResponseBody
    public ResponseEntity<User> userProfile(@PathVariable Integer id){
        User user = userService.findById(id);
        return new ResponseEntity<>(HttpStatus.OK.value(), "Find user profile success", user);
    }

    @PostMapping(value = "updateProfile/{id}")
    @ResponseBody
    public ResponseEntity<User> updateProfile(@RequestBody @Valid String profile, @PathVariable Integer id){
        User user = userService.findById(id);
        user.setProfile(profile);
        user = userService.updateProfile(user);
        return new ResponseEntity<>(HttpStatus.OK.value(), "update profile success", user);
    }

}
