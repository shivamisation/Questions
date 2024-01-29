package com.shivam.demo.controllers;

import com.shivam.demo.model.User;
import com.shivam.demo.model.UserQuestion;
import com.shivam.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:3000") // Replace with your frontend's URL
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){

        User createdUser = userService.createUser(user) ;
        return ResponseEntity.ok(createdUser);
    }
    @PostMapping("/echo")
    public ResponseEntity<?> echoUser(@RequestBody Map<String, Object> user) {

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/questions-solved")
    public ResponseEntity<List<UserQuestion>> getAllQuestionsSolvedByUser(@PathVariable Long userId) {
        List<UserQuestion> solvedQuestions = userService.getAllQuestionsSolvedByUser(userId);
        if (solvedQuestions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(solvedQuestions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
