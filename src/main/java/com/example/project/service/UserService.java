package com.example.project.service;

import com.example.project.entity.Location;
import com.example.project.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User login(Integer id, String pwd);

    User register(User user);

    User findById(Integer id);

    User updateProfile(User user);
}
