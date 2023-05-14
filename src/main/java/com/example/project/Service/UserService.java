package com.example.project.Service;

import com.example.project.Entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
//    User login(Integer id, String pwd);

//    User register(User user);

    User findById(Integer id);

    User updateProfile(User user);
}
