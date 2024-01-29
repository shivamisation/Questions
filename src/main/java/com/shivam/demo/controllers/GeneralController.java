package com.shivam.demo.controllers;

import com.shivam.demo.model.Question;
import com.shivam.demo.model.User;
import com.shivam.demo.model.UserQuestion;
import com.shivam.demo.model.UserResponseDTO;
import com.shivam.demo.repository.UserRepository;
import com.shivam.demo.service.UserQuestionService;
import com.shivam.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Replace with your frontend's URL

public class GeneralController {

    private final UserService userService;
    private final UserRepository userRepository ;
    private final UserQuestionService userQuestionService;
    @Autowired
    public GeneralController(UserService userService, UserRepository userRepository, UserQuestionService userQuestionService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userQuestionService = userQuestionService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        // Authenticate user
        boolean isAuthenticated = userService.authenticate(user.getUsername(), user.getPassword());
        if (isAuthenticated) {
            Optional<User> useropt = userRepository.findByUsername(user.getUsername()) ;
            List<Question> revisionQuestions = userQuestionService.getRevisionQuestions(useropt.get().getId());
            LocalDate today = LocalDate.now();
            List<Question> todaysQuestions = userQuestionService.getQuestionsForUserOnDate(useropt.get().getId(),today);

            UserResponseDTO userResponseDTO = new UserResponseDTO(
                    useropt.get().getId(),
                    useropt.get().getUsername(),
                    todaysQuestions,
                    revisionQuestions
            );
            return ResponseEntity.ok(userResponseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    @PostMapping("/api/signup")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            // User already exists, return a conflict response
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken");
        }
        else {
            User createdUser = userService.createUser(user);
            // Assuming no questions for a new user
            List<Question> todaysQuestions = Collections.emptyList();
            List<Question> revisionQuestions = Collections.emptyList();

            UserResponseDTO userResponseDTO = new UserResponseDTO(
                    createdUser.getId(),
                    createdUser.getUsername(),
                    todaysQuestions,
                    revisionQuestions
            );
            return ResponseEntity.ok(userResponseDTO); // You might want to omit sensitive data
        }
    }
}
