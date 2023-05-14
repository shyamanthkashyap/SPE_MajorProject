package com.example.project.Service.Impl;

import com.example.project.Entity.Blacklist;
import com.example.project.Repository.BlacklistRepository;
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
