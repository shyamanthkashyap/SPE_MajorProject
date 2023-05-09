package com.example.project.service;

import com.example.project.entity.Answers;
import com.example.project.entity.Questions;
import com.example.project.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {
    List<Answers> listAnswers(Long questionId);

    Answers saveNewAnswer(Answers answers);

    List<Answers> listMyAnswer(User user);

    Long getLikes(Answers answers);
}
