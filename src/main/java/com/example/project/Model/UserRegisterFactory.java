package com.example.project.Model;

import com.example.project.Entity.User;
import com.example.project.Service.LocationService;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.function.Function;

@Component
public class UserRegisterFactory {

    @Autowired
    private LocationService locationService;

    @Data
    @Builder
    public static class UserRegister implements Serializable {

        @NotBlank(message = "User name can not be null")
        private String username;

        @NotBlank(message = "Password can not be null")
        private String pwd;

        @NotNull(message = "Location can not be null")
        private Long locationId;
    }

    public Function<UserRegisterFactory.UserRegister, User> rpoToPojo = userRegister -> {
        User user = new User();
        user.setUsername(userRegister.getUsername());
        user.setLocation(locationService.getLocation(userRegister.getLocationId()));
        user.setPwd(userRegister.getPwd());
        return user;
    };
}


