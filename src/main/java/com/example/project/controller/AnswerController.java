package com.example.project.controller;

import com.example.project.entity.Answers;
import com.example.project.entity.Questions;
import com.example.project.entity.Thumbs;
import com.example.project.entity.User;
import com.example.project.entity.exception.SystemGlobalException;
import com.example.project.entity.response.ResponseEntity;
import com.example.project.model.AnswerDisplayFactory;
import com.example.project.model.AnswerPostFactory;
import com.example.project.service.AnswerService;
import com.example.project.service.ThumbService;
import com.example.project.service.UserService;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/answer")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerPostFactory answerPostFactory;

    @Autowired
    private UserService userService;

    @Autowired
    private ThumbService thumbService;

    @Autowired AnswerDisplayFactory answerDisplayFactory;

    @GetMapping("/list/{questionId}")
    ResponseEntity<List<AnswerDisplayFactory.AnswerDisplay>> listAnswer(@PathVariable Long questionId){
        List<Answers> answerslist = answerService.listAnswers(questionId);
        List<AnswerDisplayFactory.AnswerDisplay> res = answerslist.stream().map(answerDisplayFactory.pojoToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(HttpStatus.OK.value(), "find related answers successfully", res);
    }

    @PostMapping("/post/{id}")
    @ResponseBody
    public ResponseEntity<Answers> postAnswer(@RequestBody @Valid AnswerPostFactory.AnswerPost answerPost, @PathVariable Integer id){
        Answers answers = answerPostFactory.rpoToPojo.apply(answerPost);
        if(answers.getAnswerBody().length()==0){
            throw new SystemGlobalException("Answer can't be empty");
        }
        Integer userId = id;
        answers.setUser(userService.findById(userId));
        answers = answerService.saveNewAnswer(answers);
        return new ResponseEntity<>(HttpStatus.OK.value(), answers);
    }

    @GetMapping("/listMyAnswer/{id}")
    ResponseEntity<List<AnswerDisplayFactory.AnswerDisplay>> listMyAnswers(@PathVariable Integer id){
        User user = userService.findById(id);
        List<Answers> answerList = answerService.listMyAnswer(user);
        List<AnswerDisplayFactory.AnswerDisplay> res = answerList.stream().map(answerDisplayFactory.pojoToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(HttpStatus.OK.value(), res);
    }

    @PostMapping("like/{id}")
    ResponseEntity<Thumbs> giveLike(@RequestBody @Valid Answers answers, BindingResult bindingResult, @PathVariable Integer id){
        User user = userService.findById(id);
        Thumbs thumbs= new Thumbs();
        thumbs.setAnswers(answers);
        thumbs.setUser(user);
        Thumbs tb = thumbService.saveLike(thumbs);
        return new ResponseEntity<>(HttpStatus.OK.value(), tb);
    }
}
