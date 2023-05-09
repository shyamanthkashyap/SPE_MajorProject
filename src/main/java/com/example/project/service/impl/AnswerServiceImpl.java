package com.example.project.service.impl;

import com.example.project.entity.Answers;
import com.example.project.entity.Questions;
import com.example.project.entity.User;
import com.example.project.repository.AnswerRepository;
import com.example.project.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public List<Answers> listAnswers(Long questionId) {
        return answerRepository.queryByQuestionsQuestionId(questionId);
    }

    @Override
    public Answers saveNewAnswer(Answers answers) {
        return answerRepository.save(answers);
    }

    @Override
    public List<Answers> listMyAnswer(User user) {
        return answerRepository.findAllByUserOrderByPostTimeDesc(user);
    }

    @Override
    public Long getLikes(Answers answers) {
        return answerRepository.getLikes(answers.getAnswerId());
    }
}
