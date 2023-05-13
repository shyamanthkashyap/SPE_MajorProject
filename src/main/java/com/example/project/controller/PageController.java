package com.example.project.controller;

import com.example.project.entity.User;
import com.example.project.entity.response.ResponseEntity;
import com.example.project.model.UserRegisterFactory;
import com.example.project.service.UserService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
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

    private static final Logger logger = LogManager.getLogger(PageController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserRegisterFactory userRegisterFactory;

    @GetMapping(value = "/userProfile/{id}")
    @ResponseBody
    public ResponseEntity<User> userProfile(@PathVariable Integer id) throws Exception {
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing userProfile Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/userProfile/{id}],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try {
            logger.info(reqMessage);
            User user = userService.findById(id);
            return new ResponseEntity<>(HttpStatus.OK.value(), "Find user profile success", user);
        }
        catch(Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing userProfile\",");
            errMessage.append("method = [GET],");
            errMessage.append("path = [/userProfile/{id}],");
            errMessage.append("status = "+"ERROR,");
            errMessage.append("ExceptionMessage = "+e.getMessage());
            errMessage.append("Stacktrace = "+e.getStackTrace());
            logger.error(errMessage);
            throw new Exception();
        }
    }

    @PostMapping(value = "/updateProfile/{id}")
    @ResponseBody
    public ResponseEntity<User> updateProfile(@RequestBody @Valid String profile, @PathVariable Integer id) throws Exception{
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing updateProfile Endpoint\",");
        reqMessage.append("method = [POST],");
        reqMessage.append("path = [/updateProfile/{id}],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try {
            logger.info(reqMessage);
            User user = userService.findById(id);
            user.setProfile(profile);
            user = userService.updateProfile(user);
            return new ResponseEntity<>(HttpStatus.OK.value(), "update profile success", user);
        }
        catch (Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing updateProfile\",");
            errMessage.append("method = [POST],");
            errMessage.append("path = [/updateProfile/{id}],");
            errMessage.append("status = "+"ERROR,");
            errMessage.append("ExceptionMessage = "+e.getMessage());
            errMessage.append("Stacktrace = "+e.getStackTrace());
            logger.error(errMessage);
            throw new NullPointerException();
        }
    }

}
