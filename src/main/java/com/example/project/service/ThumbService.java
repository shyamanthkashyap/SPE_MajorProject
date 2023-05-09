package com.example.project.service;

import com.example.project.entity.Thumbs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface ThumbService {
    Thumbs saveLike(Thumbs thumbs);
}
