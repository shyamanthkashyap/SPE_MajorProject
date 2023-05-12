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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);
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
    ResponseEntity<List<AnswerDisplayFactory.AnswerDisplay>> listAnswer(@PathVariable Long questionId) throws Exception{
        try {
            logger.info("List Answer based on Question Id",
                    "method", "GET",
                    "path", "/list/{questionId}",
                    "status", HttpStatus.OK.value()
            );
            List<Answers> answerslist = answerService.listAnswers(questionId);
            logger.debug("Obtained List of Answers from Answer Service");
            List<AnswerDisplayFactory.AnswerDisplay> res = answerslist.stream().map(answerDisplayFactory.pojoToDTO).collect(Collectors.toList());
            logger.debug("Converted the List<Answers> to List<AnswerDisplay> format");
            return new ResponseEntity<>(HttpStatus.OK.value(), "find related answers successfully", res);
        }
        catch(Exception e){
            logger.error("Error Executing ListAnswers for QuestionID","status","ERROR","Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

    @PostMapping("/post/{id}")
    @ResponseBody
    public ResponseEntity<Answers> postAnswer(@RequestBody @Valid AnswerPostFactory.AnswerPost answerPost, @PathVariable Integer id) throws Exception{
        try {
            logger.info("Executing Post Answer Endpoint",
                    "method", "POST",
                    "path", "/post/{id}",
                    "status", HttpStatus.OK.value()
            );
            Answers answers = answerPostFactory.rpoToPojo.apply(answerPost);
            if (answers.getAnswerBody().length() == 0) {
                logger.error("Answer is Empty");
                throw new SystemGlobalException("Answer can't be empty");
            }
            Integer userId = id;
            answers.setUser(userService.findById(userId));
            answers = answerService.saveNewAnswer(answers);
            logger.debug("Answer is saved to the database");
            return new ResponseEntity<>(HttpStatus.OK.value(), answers);
        }
        catch(Exception e){
            logger.error("Error while executing postAnswers","status","ERROR","Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

    @GetMapping("/listMyAnswer/{id}")
    ResponseEntity<List<AnswerDisplayFactory.AnswerDisplay>> listMyAnswers(@PathVariable Integer id) throws Exception{
        try {
            logger.info("Executing listMyAnswers Endpoint",
                    "method", "GET",
                    "path", "/listMyAnswer/{id}",
                    "status", HttpStatus.OK.value()
            );
            User user = userService.findById(id);
            List<Answers> answerList = answerService.listMyAnswer(user);
            List<AnswerDisplayFactory.AnswerDisplay> res = answerList.stream().map(answerDisplayFactory.pojoToDTO).collect(Collectors.toList());
            logger.debug("Obtained list of answers in AnswerDisplay format");
            return new ResponseEntity<>(HttpStatus.OK.value(), res);
        }
        catch(Exception e){
            logger.error("Error while executing listMyAnswers function","status","ERROR","Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

    @PostMapping("/like/{id}")
    ResponseEntity<Thumbs> giveLike(@RequestBody @Valid Answers answers, BindingResult bindingResult, @PathVariable Integer id) throws Exception{
        try {
            logger.info("Executing givelike Endpoint",
                    "method", "POST",
                    "path", "/like/{id}",
                    "status", HttpStatus.OK.value()
            );
            User user = userService.findById(id);
            Thumbs thumbs = new Thumbs();
            thumbs.setAnswers(answers);
            thumbs.setUser(user);
            Thumbs tb = thumbService.saveLike(thumbs);
            logger.debug("The given like is persisted to the database");
            return new ResponseEntity<>(HttpStatus.OK.value(), tb);
        }
        catch (Exception e){
            logger.error("Error while executing giveLike function","status","ERROR","Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }
}
