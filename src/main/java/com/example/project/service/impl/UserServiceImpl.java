package com.example.project.service.impl;

import com.example.project.entity.User;
import com.example.project.entity.exception.SystemGlobalException;
import com.example.project.repository.UserRepository;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;



    @Override
    public User login(Long id, String pwd) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new SystemGlobalException("No such user");
        }

        User loginUser = user.get();
        if(!pwd.equals(loginUser.getPwd())){
            throw new SystemGlobalException("The account sign-in was incorrect");
        }
        return loginUser;

    }

    @Override
    public User register(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User updateProfile(User user) {
        return userRepository.saveAndFlush(user);
    }
}
