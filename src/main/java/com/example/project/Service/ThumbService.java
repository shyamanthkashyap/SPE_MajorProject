package com.example.project.Service;

import com.example.project.Entity.Thumbs;
import org.springframework.stereotype.Service;

@Service
public interface ThumbService {
    Thumbs saveLike(Thumbs thumbs);
}
