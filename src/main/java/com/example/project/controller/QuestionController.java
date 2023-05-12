package com.example.project.controller;

import com.example.project.entity.Questions;
import com.example.project.entity.User;
import com.example.project.entity.exception.SystemGlobalException;
import com.example.project.entity.response.ResponseEntity;
import com.example.project.model.QuestionDisplayFactory;
import com.example.project.model.QuestionPostFactory;
import com.example.project.service.QuestionService;
import com.example.project.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/question")
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
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
    ResponseEntity<List<Questions>> listAll() throws Exception{
        try {
            logger.info("Executing listAll Endpoint",
                    "method", "GET",
                    "path", "/listAll",
                    "status", HttpStatus.OK.value()
            );
            List<Questions> questionsList = questionService.listAll();
            return new ResponseEntity<>(HttpStatus.OK.value(), "Find all questions success", questionsList);
        }
        catch(Exception e){
            logger.error("Error Executing listAll","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

    @GetMapping("/list/{questionId}")
    ResponseEntity<Questions> listQuestion(@PathVariable Long questionId) throws Exception{
        try {
            logger.info("Executing listQuestion Endpoint",
                    "method", "GET",
                    "path", "/list/{questionId}",
                    "status", HttpStatus.OK.value()
            );
            Questions questions = questionService.listOneQuestion(questionId);
            return new ResponseEntity<>(HttpStatus.OK.value(), "Find question success", questions);
        }
        catch (Exception e){
            logger.error("Error Executing listQuestion","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

    @PostMapping(value = "/post/{id}")
    @ResponseBody
    public ResponseEntity<Questions> register(@RequestBody @Valid QuestionPostFactory.QuestionPost questionPost, @PathVariable Integer id) throws Exception {
        try {
            logger.info("Executing register Endpoint",
                    "method", "POST",
                    "path", "/post/{id}",
                    "status", HttpStatus.OK.value()
            );
            Questions questions = questionPostFactory.rpoToPojo.apply(questionPost);
            if (questions.getTitle().length() == 0) {
                throw new SystemGlobalException("Question Title can't be null");
            }
            questions.setUser(userService.findById(id));
            questionService.saveNewQuestion(questions);
            return new ResponseEntity<>(HttpStatus.OK.value(), "Save new question", questions);
        }
        catch(Exception e){
            logger.error("Error Executing register","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

    @GetMapping("/listCategory/{ctgyId}")
    ResponseEntity<List<Questions>> listCategoryQuestion(@PathVariable Long ctgyId) throws Exception{
        try {
            logger.info("Executing listCategoryQuestion Endpoint",
                    "method", "GET",
                    "path", "/listCategory/{ctgyId}",
                    "status", HttpStatus.OK.value()
            );
            List<Questions> questionsList = questionService.listCatgoryQuestions(ctgyId);
            return new ResponseEntity<>(HttpStatus.OK.value(), questionsList);
        }
        catch (Exception e){
            logger.error("Error Executing listCategoryQuestion","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

    @GetMapping("/listSubCategory/{ctgyId}")
    ResponseEntity<List<Questions>> listSubCategoryQuestion(@PathVariable Long ctgyId) throws Exception{
        try {
            logger.info("Executing listSubCategoryQuestion Endpoint",
                    "method", "GET",
                    "path", "/listSubCategory/{ctgyId}",
                    "status", HttpStatus.OK.value()
            );
            List<Questions> questionsList = questionService.listSubCatgoryQuestions(ctgyId);
            return new ResponseEntity<>(HttpStatus.OK.value(), questionsList);
        }
        catch (Exception e){
            logger.error("Error Executing listSubCategoryQuestion","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

    @GetMapping("/listMyQuestion/{id}")
    ResponseEntity<List<Questions>> listMyQuestions(@PathVariable Integer id) throws Exception{
        try {
            logger.info("Executing listMyQuestions Endpoint",
                    "method", "GET",
                    "path", "/listMyQuestion/{id}",
                    "status", HttpStatus.OK.value()
            );
            User user = userService.findById(id);
            List<Questions> questionsList = questionService.listMyQuestions(user);
            return new ResponseEntity<>(HttpStatus.OK.value(), questionsList);
        }
        catch (Exception e){
            logger.error("Error Executing listMyQuestions","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }
}
