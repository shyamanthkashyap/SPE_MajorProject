package com.example.project.Service.Impl;

import com.example.project.Entity.User;
import com.example.project.Repository.UserRepository;
import com.example.project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;



//    @Override
//    public User login(Integer id, String pwd) {
//        Optional<User> user = userRepository.findById(id);
//        if(user.isEmpty()){
//            throw new SystemGlobalException("No such user");
//        }
//
//        User loginUser = user.get();
//        if(!pwd.equals(loginUser.getPwd())){
//            throw new SystemGlobalException("The account sign-in was incorrect");
//        }
//        return loginUser;
//
//    }

//    @Override
//    public User register(User user) {
//        return userRepository.save(user);
//    }

    @Override
    public User findById(Integer id) {
        return userRepository.findUserByUserId(id);
    }

    @Override
    public User updateProfile(User user) {
        return userRepository.saveAndFlush(user);
    }
}
