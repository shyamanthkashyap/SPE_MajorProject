package com.example.project.model;


import com.example.project.entity.Answers;
import com.example.project.entity.Questions;
import com.example.project.entity.User;
import com.example.project.service.AnswerService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.function.Function;

@Component
public class AnswerDisplayFactory {

    @Autowired
    private AnswerService answerService;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnswerDisplay implements Serializable{
        @NotBlank(message = "Answer can't be empty")
        private String body;

        @NotNull(message = "Question can't be null")
        private Questions questions;

        @NotNull(message = "User can't be null")
        private User user;

        @NotNull(message = "answer id can't be null")
        private Long answerId;

        @NotNull(message = "post time can't be null")
        private java.sql.Timestamp postTime;

        @NotNull(message = "likes can't be null")
        private Long likes;
    }

    public Function<Answers, AnswerDisplay> pojoToDTO = answers -> {
        AnswerDisplay answerDisplay = new AnswerDisplay();
        answerDisplay.setAnswerId(answers.getAnswerId());
        answerDisplay.setBody(answers.getAnswerBody());
        answerDisplay.setPostTime(answers.getPostTime());
        answerDisplay.setUser(answers.getUser());
        answerDisplay.setQuestions(answers.getQuestions());
        answerDisplay.setLikes(answerService.getLikes(answers));
        return answerDisplay;
    };
}
