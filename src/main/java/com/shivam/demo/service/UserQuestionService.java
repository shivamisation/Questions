package com.shivam.demo.service;

import com.shivam.demo.model.Question;
import com.shivam.demo.model.User;
import com.shivam.demo.model.UserQuestion;
import com.shivam.demo.repository.QuestionRepository;
import com.shivam.demo.repository.UserQuestionRepository;
import com.shivam.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserQuestionService {
    private final UserQuestionRepository userQuestionRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public UserQuestionService(UserQuestionRepository userQuestionRepository, UserRepository userRepository, QuestionRepository questionRepository) {
        this.userQuestionRepository = userQuestionRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    public UserQuestion recordSolvedQuestion(String username, String questionName, String link, String hint, int difficulty, int importance) {
        // Find or create user
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        // Find or create question
        Question question = questionRepository.findByName(questionName)
                .orElseGet(() -> createNewQuestion(questionName, link, hint, difficulty, importance));

        // Create and save UserQuestion
        UserQuestion userQuestion = new UserQuestion();
        userQuestion.setUser(user);
        userQuestion.setQuestion(question);
        userQuestion.setDateSolved(LocalDate.now());

        return userQuestionRepository.save(userQuestion);
    }

    private Question createNewQuestion(String name, String link, String hint, int difficulty, int importance) {
        Question newQuestion = new Question();
        newQuestion.setName(name);
        newQuestion.setLink(link);
        newQuestion.setHint(hint);
        newQuestion.setDifficulty(difficulty);
        newQuestion.setImportnace(importance);

        return questionRepository.save(newQuestion);
    }


    public List<Question> getQuestionsForUserOnDate(Long userId , LocalDate date){
        return userQuestionRepository.findByUserIdAndDateSolved(userId,date).stream().map(UserQuestion::getQuestion)
                .collect(Collectors.toList());
    }

    public List<Question> getRevisionQuestions(Long userId) {
        List<UserQuestion> revisionQuestions = new ArrayList<>();
        LocalDate today = LocalDate.now();
        // Add questions from 1, 3, 7, 21, and 30 days ago
        List.of(1, 3, 7, 21, 30).forEach(daysAgo ->
                revisionQuestions.addAll(userQuestionRepository.findByUserIdAndDateSolved(userId, today.minusDays(daysAgo)))
        );
        return revisionQuestions.stream().map(UserQuestion::getQuestion)
                .collect(Collectors.toList());
    }

    public boolean deleteUserQuestion(String username, String questionName, LocalDate dateSolved) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Question> question = questionRepository.findByName(questionName);

        if (user.isPresent() && question.isPresent()) {
            List<UserQuestion> userQuestions = userQuestionRepository.findByUserIdAndQuestionIdAndDateSolved(
                    user.get().getId(), question.get().getId(), dateSolved);

            if (!userQuestions.isEmpty()) {
                // Delete only the first matched UserQuestion
                userQuestionRepository.delete(userQuestions.get(0));
                return true;
            }
        }
        return false;
    }

}
