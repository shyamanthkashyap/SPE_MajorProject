package com.example.project.Service;

import com.example.project.Entity.Answers;
import com.example.project.Entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {
    List<Answers> listAnswers(Long questionId);

    Answers saveNewAnswer(Answers answers);

    List<Answers> listMyAnswer(User user);

    Long getLikes(Answers answers);
}
