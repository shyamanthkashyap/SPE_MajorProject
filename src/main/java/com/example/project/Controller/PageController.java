package com.example.project.Controller;

import com.example.project.Entity.User;
import com.example.project.Entity.Response.ResponseEntity;
import com.example.project.Model.UserRegisterFactory;
import com.example.project.Service.UserService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
        reqMessage.append("path = [/api/user/userProfile/{id}],");
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
            errMessage.append("path = [/api/user/userProfile/{id}],");
            errMessage.append("status = "+500);
            errMessage.append(",ExceptionMessage = "+e.getMessage());
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
        reqMessage.append("path = [/api/user/updateProfile/{id}],");
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
            errMessage.append("path = [/api/user/updateProfile/{id}],");
            errMessage.append("status = "+500);
            errMessage.append(",ExceptionMessage = "+e.getMessage());
            logger.error(errMessage);
            throw new NullPointerException();
        }
    }


    @GetMapping(value="/updatepoints/{userId}")
    @ResponseBody
    public ResponseEntity<Optional> updatePoints(@PathVariable Integer userId) throws Exception {
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing updatePoints Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/api/user/updatepoints/{userId}],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try{
            User user = userService.findById(userId);
            Long pts = user.getPoints();
            pts+=1;
            user.setPoints(pts);
            user = userService.updateProfile(user);
            logger.info(reqMessage);
            return new ResponseEntity<>(HttpStatus.OK.value(), "update Points success");
        }
        catch(Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing updatePoints\",");
            errMessage.append("method = [GET],");
            errMessage.append("path = [/api/user/updatepoints/{userId}],");
            errMessage.append("status = "+500);
            errMessage.append(",ExceptionMessage = "+e.getMessage());
            throw new Exception();
        }
    }

}
