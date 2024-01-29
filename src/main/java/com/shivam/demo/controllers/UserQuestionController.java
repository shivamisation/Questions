package com.shivam.demo.controllers;

import com.shivam.demo.model.Question;
import com.shivam.demo.model.User;
import com.shivam.demo.model.UserQuestion;
import com.shivam.demo.service.UserQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Replace with your frontend's URL
@RequestMapping("/api/user-questions")
public class UserQuestionController {

    private final UserQuestionService userQuestionService;

    @Autowired
    public UserQuestionController(UserQuestionService userQuestionService) {
        this.userQuestionService = userQuestionService;
    }

    @PostMapping("/add-question")
    public ResponseEntity<UserQuestion> recordSolvedQuestion(
            @RequestParam String username,
            @RequestParam String questionName,
            @RequestParam String link,
            @RequestParam String hint,
            @RequestParam int difficulty,
            @RequestParam int importance) {

        UserQuestion recordedQuestion = userQuestionService.recordSolvedQuestion(
                username, questionName, link, hint, difficulty, importance);
        return ResponseEntity.ok(recordedQuestion);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserQuestion(
            @RequestParam String username,
            @RequestParam String questionName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateSolved) {
        boolean deleted = userQuestionService.deleteUserQuestion(username, questionName, dateSolved);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
