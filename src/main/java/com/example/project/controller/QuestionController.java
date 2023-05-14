package com.example.project.controller;

import com.example.project.entity.Questions;
import com.example.project.entity.Thumbs;
import com.example.project.entity.User;
import com.example.project.entity.exception.SystemGlobalException;
import com.example.project.entity.response.ResponseEntity;
import com.example.project.model.QuestionDisplayFactory;
import com.example.project.model.QuestionPostFactory;
import com.example.project.service.QuestionService;
import com.example.project.service.UserService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
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

    private static final Logger logger = LogManager.getLogger(QuestionController.class);
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
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing listAll\",");
            errMessage.append("method = [GET],");
            errMessage.append("path = [/listAll],");
            errMessage.append("status = "+"ERROR,");
            errMessage.append("ExceptionMessage = "+e.getMessage());
            errMessage.append("Stacktrace = "+e.getStackTrace());
            logger.error(errMessage);
            throw new Exception();
        }
    }

    @GetMapping("/list/{questionId}")
    ResponseEntity<Questions> listQuestion(@PathVariable Long questionId) throws Exception{
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing listQuestion Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/list/{questionId}],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try {
            logger.info(reqMessage);
            Questions questions = questionService.listOneQuestion(questionId);
            return new ResponseEntity<>(HttpStatus.OK.value(), "Find question success", questions);
        }
        catch (Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing listQuestion\",");
            errMessage.append("method = [GET],");
            errMessage.append("path = [/list/{questionId}],");
            errMessage.append("status = "+"ERROR,");
            errMessage.append("ExceptionMessage = "+e.getMessage());
            errMessage.append("Stacktrace = "+e.getStackTrace());
            logger.error(errMessage);
            throw new Exception();
        }
    }

    @PostMapping(value = "/post/{id}")
    @ResponseBody
    public ResponseEntity<Questions> register(@RequestBody @Valid QuestionPostFactory.QuestionPost questionPost, @PathVariable Integer id) throws Exception {
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing register Endpoint\",");
        reqMessage.append("method = [POST],");
        reqMessage.append("path = [/post/{id}],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try {
            logger.info(reqMessage);
            Questions questions = questionPostFactory.rpoToPojo.apply(questionPost);
            if (questions.getTitle().length() == 0) {
                throw new SystemGlobalException("Question Title can't be null");
            }
            questions.setUser(userService.findById(id));
            questionService.saveNewQuestion(questions);
            return new ResponseEntity<>(HttpStatus.OK.value(), "Save new question", questions);
        }
        catch(Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing register\",");
            errMessage.append("method = [POST],");
            errMessage.append("path = [/post/{id}],");
            errMessage.append("status = "+"ERROR,");
            errMessage.append("ExceptionMessage = "+e.getMessage());
            errMessage.append("Stacktrace = "+e.getStackTrace());
            logger.error(errMessage);
            throw new Exception();
        }
    }

    @GetMapping("/listCategory/{ctgyId}")
    ResponseEntity<List<Questions>> listCategoryQuestion(@PathVariable Long ctgyId) throws Exception{
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing listCategoryQuestion Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/listCategory/{ctgyId}],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try {
            logger.info(reqMessage);
            List<Questions> questionsList = questionService.listCatgoryQuestions(ctgyId);
            return new ResponseEntity<>(HttpStatus.OK.value(), questionsList);
        }
        catch (Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing listCategoryQuestion\",");
            errMessage.append("method = [GET],");
            errMessage.append("path = [/listCategory/{ctgyId}],");
            errMessage.append("status = "+"ERROR,");
            errMessage.append("ExceptionMessage = "+e.getMessage());
            errMessage.append("Stacktrace = "+e.getStackTrace());
            logger.error(errMessage);
            throw new Exception();
        }
    }

    @GetMapping("/listSubCategory/{ctgyId}")
    ResponseEntity<List<Questions>> listSubCategoryQuestion(@PathVariable Long ctgyId) throws Exception{
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing listSubCategoryQuestion Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/listSubCategory/{ctgyId}],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try {
            logger.info(reqMessage);
            List<Questions> questionsList = questionService.listSubCatgoryQuestions(ctgyId);
            return new ResponseEntity<>(HttpStatus.OK.value(), questionsList);
        }
        catch (Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing listSubCategoryQuestion\",");
            errMessage.append("method = [GET],");
            errMessage.append("path = [/listSubCategory/{ctgyId}],");
            errMessage.append("status = "+"ERROR,");
            errMessage.append("ExceptionMessage = "+e.getMessage());
            errMessage.append("Stacktrace = "+e.getStackTrace());
            logger.error(errMessage);
            throw new Exception();
        }
    }

    @GetMapping("/listMyQuestion/{id}")
    ResponseEntity<List<Questions>> listMyQuestions(@PathVariable Integer id) throws Exception{
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing listMyQuestions Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/listMyQuestion/{id}],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try {
            logger.info(reqMessage);
            User user = userService.findById(id);
            List<Questions> questionsList = questionService.listMyQuestions(user);
            return new ResponseEntity<>(HttpStatus.OK.value(), questionsList);
        }
        catch (Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing listMyQuestions\",");
            errMessage.append("method = [GET],");
            errMessage.append("path = [/listMyQuestion/{id}],");
            errMessage.append("status = "+"ERROR,");
            errMessage.append("ExceptionMessage = "+e.getMessage());
            errMessage.append("Stacktrace = "+e.getStackTrace());
            logger.error(errMessage);
            throw new Exception();
        }
    }

    @GetMapping("/listRelated/{text}")
    ResponseEntity<List<QuestionDisplayFactory.QuestionDisplay>> listRelatedQuestion(@PathVariable String text){
        List<Questions> questions = questionService.listRelatedQuestion(text);
        List<QuestionDisplayFactory.QuestionDisplay> questionDisplayList = questions.stream().map(questionDisplayFactory.PojoToDTO).collect(Collectors.toList());
        for(int i=0; i<questionDisplayList.size(); i++){
            questionDisplayList.get(i).setId(i);
        }
        return new ResponseEntity<>(HttpStatus.OK.value(), "Find related questions success", questionDisplayList);
    }

    @PostMapping("/updateBestAnswer/{questionId}")
    ResponseEntity<Optional> updateQuestion(@RequestBody Questions question , @PathVariable Long questionId) throws Exception {
        try{
            System.out.println("Best Answer called");
            if(question.getQuestionId()==questionId){
                questionService.updateBestAnswer(question.getQuestionId(),question.getBestAnswerId());
                return new ResponseEntity<>(HttpStatus.OK.value(),"Updated Best Answer");
            }
            else{
                throw new Exception("Question and Id doesn't match");
            }
        }
        catch (Exception e){
            throw new Exception();
        }
    }

}
