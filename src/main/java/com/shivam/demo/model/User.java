package com.shivam.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false,unique = true)
    private String username ;

    @Column(nullable = false)
    private String password ;

    @OneToMany(mappedBy = "user")
//    @JsonManagedReference
    private Set<UserQuestion> solvedQuestions ;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserQuestion> getSolvedQuestions() {
        return solvedQuestions;
    }

    public void setSolvedQuestions(Set<UserQuestion> solvedQuestions) {
        this.solvedQuestions = solvedQuestions;
    }


}
