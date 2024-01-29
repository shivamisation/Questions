package com.shivam.demo.model;

import java.util.List;

public class UserResponseDTO {
    private Long id;
    private String username;
    private List<Question> todaysQuestions;
    private List<Question> revisionQuestions;

    public UserResponseDTO(Long id, String username, List<Question> todaysQuestions, List<Question> revisionQuestions) {
        this.id = id;
        this.username = username;
        this.todaysQuestions = todaysQuestions;
        this.revisionQuestions = revisionQuestions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Question> getTodaysQuestions() {
        return todaysQuestions;
    }

    public void setTodaysQuestions(List<Question> todaysQuestions) {
        this.todaysQuestions = todaysQuestions;
    }

    public List<Question> getRevisionQuestions() {
        return revisionQuestions;
    }

    public void setRevisionQuestions(List<Question> revisionQuestions) {
        this.revisionQuestions = revisionQuestions;
    }
}
