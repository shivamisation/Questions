package com.shivam.demo.service;

import com.shivam.demo.model.User;
import com.shivam.demo.model.UserQuestion;
import com.shivam.demo.repository.UserQuestionRepository;
import com.shivam.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserQuestionRepository userQuestionRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserQuestionRepository userQuestionRepository) {
        this.userRepository = userRepository;
        this.userQuestionRepository = userQuestionRepository;
    }

    public User createUser(User user){
        return (User) userRepository.save(user);
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public List<UserQuestion> getAllQuestionsSolvedByUser(Long userId) {
        // This will retrieve all the UserQuestion instances for a specific user
        // And then return them directly. If you want just the Question part, you would need to map it.
        return userQuestionRepository.findByUserId(userId);
    }



    public boolean authenticate(String username, String password) {
        Optional<User> foundUser = userRepository.findByUsername(username);

        if (foundUser.isPresent()) {
            User user = foundUser.get();
            // In real-world applications, use hashed passwords
            return user.getPassword().equals(password);
        }

        return false;
    }
}
