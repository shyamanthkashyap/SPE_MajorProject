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
import javax.validation.constraints.Null;

@Controller
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PageController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRegisterFactory userRegisterFactory;

    @GetMapping(value = "/userProfile/{id}")
    @ResponseBody
    public ResponseEntity<User> userProfile(@PathVariable Integer id){
        User user = userService.findById(id);
        return new ResponseEntity<>(HttpStatus.OK.value(), "Find user profile success", user);
    }

    @PostMapping(value = "updateProfile/{id}")
    @ResponseBody
    public ResponseEntity<User> updateProfile(@RequestBody @Valid String profile, @PathVariable Integer id) throws Exception{
        try {
            User user = userService.findById(id);
            user.setProfile(profile);
            user = userService.updateProfile(user);
            return new ResponseEntity<>(HttpStatus.OK.value(), "update profile success", user);
        }
        catch (Exception e){
            throw new NullPointerException();
        }
    }

}
