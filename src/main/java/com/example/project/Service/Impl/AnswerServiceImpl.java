package com.example.project.Service.Impl;

import com.example.project.Entity.Answers;
import com.example.project.Entity.User;
import com.example.project.Repository.AnswerRepository;
import com.example.project.Service.AnswerService;
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
