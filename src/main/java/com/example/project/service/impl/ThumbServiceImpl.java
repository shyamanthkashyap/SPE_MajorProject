package com.example.project.service.impl;

import com.example.project.entity.Thumbs;
import com.example.project.repository.ThumbRepository;
import com.example.project.service.ThumbService;
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
