package com.example.project.controller;

import com.example.project.entity.Questions;
import com.example.project.entity.User;
import com.example.project.entity.exception.SystemGlobalException;
import com.example.project.entity.response.ResponseEntity;
import com.example.project.model.QuestionDisplayFactory;
import com.example.project.model.QuestionPostFactory;
import com.example.project.service.QuestionService;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/question")
@CrossOrigin("http://localhost:3000")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionDisplayFactory questionDisplayFactory;

    @Autowired
    private QuestionPostFactory questionPostFactory;

    @Autowired
    private UserService userService;

    @GetMapping("/listAll")
    @ResponseBody
    ResponseEntity<List<Questions>> listAll(){
        List<Questions> questionsList = questionService.listAll();
        return new ResponseEntity<>(HttpStatus.OK.value(), "Find all questions success", questionsList);

    }

    @GetMapping("/list/{questionId}")
    ResponseEntity<Questions> listQuestion(@PathVariable Long questionId){
        Questions questions = questionService.listOneQuestion(questionId);
        return new ResponseEntity<>(HttpStatus.OK.value(), "Find question success", questions);
    }

    @PostMapping(value = "/post/{id}")
    @ResponseBody
    public ResponseEntity<Questions> register(@RequestBody @Valid QuestionPostFactory.QuestionPost questionPost, @PathVariable Integer id){
        Questions questions = questionPostFactory.rpoToPojo.apply(questionPost);
        if(questions.getTitle().length()==0){
            throw new SystemGlobalException("Question Title can't be null");
        }
        questions.setUser(userService.findById(id));
        questionService.saveNewQuestion(questions);
        return new ResponseEntity<>(HttpStatus.OK.value(), "Save new question", questions);
    }

    @GetMapping("/listCategory/{ctgyId}")
    ResponseEntity<List<Questions>> listCategoryQuestion(@PathVariable Long ctgyId){
        List<Questions> questionsList = questionService.listCatgoryQuestions(ctgyId);
        return new ResponseEntity<>(HttpStatus.OK.value(), questionsList);
    }

    @GetMapping("/listSubCategory/{ctgyId}")
    ResponseEntity<List<Questions>> listSubCategoryQuestion(@PathVariable Long ctgyId){
        List<Questions> questionsList = questionService.listSubCatgoryQuestions(ctgyId);
        return new ResponseEntity<>(HttpStatus.OK.value(), questionsList);
    }

    @GetMapping("/listMyQuestion/{id}")
    ResponseEntity<List<Questions>> listMyQuestions(@PathVariable Integer id){
        User user = userService.findById(id);
        List<Questions> questionsList = questionService.listMyQuestions(user);
        return new ResponseEntity<>(HttpStatus.OK.value(), questionsList);
    }
}
