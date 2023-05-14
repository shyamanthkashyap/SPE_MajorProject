package com.example.project.Model;


import com.example.project.Entity.Answers;
import com.example.project.Entity.Questions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.function.Function;

@Component
public class AnswerPostFactory{

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerPost implements Serializable{

        @NotBlank(message = "Answer can't be empty")
        private String body;

        @NotNull(message = "Question can't be null")
        private Questions questions;
    }

    public Function<AnswerPost, Answers> rpoToPojo = answerPost -> {
        Answers answers = new Answers();
        answers.setAnswerBody(answerPost.getBody());
        answers.setPostTime(new Timestamp(System.currentTimeMillis()));
        answers.setQuestions(answerPost.getQuestions());
        return answers;
    };
}
