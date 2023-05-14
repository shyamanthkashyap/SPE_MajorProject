package com.example.project.Controller;

import com.example.project.Entity.Questions;
import com.example.project.Entity.User;
import com.example.project.Entity.Exception.SystemGlobalException;
import com.example.project.Entity.Response.ResponseEntity;
import com.example.project.Model.QuestionDisplayFactory;
import com.example.project.Model.QuestionPostFactory;
import com.example.project.Service.QuestionService;
import com.example.project.Service.UserService;
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
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing listAll Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/api/question/listAll],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try {
            logger.info(reqMessage);
            List<Questions> questionsList = questionService.listAll();
            return new ResponseEntity<>(HttpStatus.OK.value(), "Find all questions success", questionsList);
        }
        catch(Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing listAll\",");
            errMessage.append("method = [GET],");
            errMessage.append("path = [/api/question/listAll],");
            errMessage.append("status = "+500);
            errMessage.append(",ExceptionMessage = "+e.getMessage());
            logger.error(errMessage);
            throw new Exception();
        }
    }

    @GetMapping("/list/{questionId}")
    ResponseEntity<Questions> listQuestion(@PathVariable Long questionId) throws Exception{
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing listQuestion Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/api/question/list/{questionId}],");
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
            errMessage.append("path = [/api/question/list/{questionId}],");
            errMessage.append("status = "+500);
            errMessage.append(",ExceptionMessage = "+e.getMessage());
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
        reqMessage.append("path = [/api/question/post/{id}],");
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
            errMessage.append("path = [/api/question/post/{id}],");
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
        reqMessage.append("path = [/api/question/listCategory/{ctgyId}],");
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
            errMessage.append("path = [/api/question/listCategory/{ctgyId}],");
            errMessage.append("status = "+500);
            errMessage.append(",ExceptionMessage = "+e.getMessage());
            logger.error(errMessage);
            throw new Exception();
        }
    }

    @GetMapping("/listSubCategory/{ctgyId}")
    ResponseEntity<List<Questions>> listSubCategoryQuestion(@PathVariable Long ctgyId) throws Exception{
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing listSubCategoryQuestion Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/api/question/listSubCategory/{ctgyId}],");
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
            errMessage.append("path = [/api/question/listSubCategory/{ctgyId}],");
            errMessage.append("status = "+500);
            errMessage.append(",ExceptionMessage = "+e.getMessage());
            logger.error(errMessage);
            throw new Exception();
        }
    }

    @GetMapping("/listMyQuestion/{id}")
    ResponseEntity<List<Questions>> listMyQuestions(@PathVariable Integer id) throws Exception{
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing listMyQuestions Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/api/question/listMyQuestion/{id}],");
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
            errMessage.append("path = [/api/question/listMyQuestion/{id}],");
            errMessage.append("status = "+500);
            errMessage.append(",ExceptionMessage = "+e.getMessage());
            logger.error(errMessage);
            throw new Exception();
        }
    }

    @GetMapping("/listRelated/{text}")
    ResponseEntity<List<QuestionDisplayFactory.QuestionDisplay>> listRelatedQuestion(@PathVariable String text) throws Exception{
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing listRelatedQuestions Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/api/question/listRelated/{text}],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try {
            List<Questions> questions = questionService.listRelatedQuestion(text);
            List<QuestionDisplayFactory.QuestionDisplay> questionDisplayList = questions.stream().map(questionDisplayFactory.PojoToDTO).collect(Collectors.toList());
            for (int i = 0; i < questionDisplayList.size(); i++) {
                questionDisplayList.get(i).setId(i);
            }
            logger.info(reqMessage);
            return new ResponseEntity<>(HttpStatus.OK.value(), "Find related questions success", questionDisplayList);
        }
        catch(Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing listRelatedQuestions\",");
            errMessage.append("method = [GET],");
            errMessage.append("path = [/api/question/listRelated/{text}],");
            errMessage.append("status = "+500);
            errMessage.append(",ExceptionMessage = "+e.getMessage());
            logger.error(errMessage);
            throw new Exception();
        }
    }


    @PostMapping("/updateBestAnswer/{questionId}")
    ResponseEntity<Optional> updateBestAnswer(@RequestBody Questions question , @PathVariable Long questionId) throws Exception {
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing updateBestAnswer Endpoint\",");
        reqMessage.append("method = [POST],");
        reqMessage.append("path = [/api/question/updateBestAnswer/{questionId}],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try{
            System.out.println("Best Answer called");
            if(question.getQuestionId()==questionId){
                questionService.updateBestAnswer(question.getQuestionId(),question.getBestAnswerId());
                logger.info(reqMessage);
                return new ResponseEntity<>(HttpStatus.OK.value(),"Updated Best Answer");
            }
            else{
                throw new Exception("Question and Id doesn't match");
            }
        }
        catch (Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing updateBestAnswer\",");
            errMessage.append("method = [POST],");
            errMessage.append("path = [/api/question/updateBestAnswer/{questionId}],");
            errMessage.append("status = "+500);
            errMessage.append(",ExceptionMessage = "+e.getMessage());
            logger.error(errMessage);
            throw new Exception();
        }
    }

}
