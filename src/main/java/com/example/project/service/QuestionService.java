package com.example.project.service;

import com.example.project.entity.Questions;
import com.example.project.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {


    List<Questions> listAll();

    Questions listOneQuestion(Long questionId);

    Questions saveNewQuestion(Questions questions);

    List<Questions> listCatgoryQuestions(Long ctgyId);

    List<Questions> listSubCatgoryQuestions(Long ctgyId);

    List<Questions> listRelatedQuestion(String text);

    List<Questions> listMyQuestions(User user);

    void updateBestAnswer(Long questionId, Long bestAnswerId);
}
