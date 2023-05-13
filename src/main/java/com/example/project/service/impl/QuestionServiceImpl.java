package com.example.project.service.impl;

import com.example.project.entity.Questions;
import com.example.project.entity.User;
import com.example.project.entity.exception.SystemGlobalException;
import com.example.project.repository.QuestionRepository;
import com.example.project.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Questions> listAll() {
        return questionRepository.findAll(Sort.by(Sort.Direction.DESC, "postTime"));
    }

    @Override
    public Questions listOneQuestion(Long questionId) {
        Optional<Questions> question = questionRepository.findById(questionId);
        if(question.isEmpty()){
            throw new SystemGlobalException("No such question");
        }
        return question.get();
    }
    @Override
    public Questions saveNewQuestion(Questions questions){
        return questionRepository.saveAndFlush(questions);
    }

    @Override
    public List<Questions> listCatgoryQuestions(Long ctgyId) {
        return questionRepository.listCatgoryQuestions(ctgyId);
    }

    @Override
    public List<Questions> listRelatedQuestion(String text) {
        return questionRepository.findRelatedQuestions(text);
    }

    @Override
    public List<Questions> listSubCatgoryQuestions(Long ctgyId) {
        return questionRepository.findAllBySubCategoryId(ctgyId);
    }

    @Override
    public List<Questions> listMyQuestions(User user) {
        return questionRepository.findAllByUserOrderByPostTimeDesc(user);
    }

    @Override
    public void updateBestAnswer(Long questionId, Long bestAnswerId) {
        questionRepository.updateBestAnswer(questionId,bestAnswerId);
    }
}
