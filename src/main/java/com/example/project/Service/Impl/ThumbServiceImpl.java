package com.example.project.Service.Impl;

import com.example.project.Entity.Thumbs;
import com.example.project.Repository.ThumbRepository;
import com.example.project.Service.ThumbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThumbServiceImpl implements ThumbService {

    @Autowired
    ThumbRepository thumbRepository;

    @Override
    public Thumbs saveLike(Thumbs thumbs) {
        Optional<Thumbs> tb= thumbRepository.getThumbsByAnswersAndUser(thumbs.getAnswers(), thumbs.getUser());
        if(!tb.isEmpty()){
            return tb.get();
        }
        return thumbRepository.saveAndFlush(thumbs);
    }
}
