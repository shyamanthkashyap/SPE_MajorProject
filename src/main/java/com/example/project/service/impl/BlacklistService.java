package com.example.project.service.impl;

import com.example.project.entity.Blacklist;
import com.example.project.repository.BlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlacklistService {

    @Autowired
    BlacklistRepository blacklistRepository;
    public void setToken(Blacklist token){
        blacklistRepository.save(token);
    }

    public boolean isTokenAvailable(String token){
        String bearer= "Bearer "+token;
        String a=blacklistRepository.isTokenAvailable(bearer);
        if(a!=null){
            return true;
        }
        else{
            return false;
        }
    }

    public void deleteRefreshTokenUser(Integer id){
        blacklistRepository.deleteRefreshTokenUser(id);
    }

}
