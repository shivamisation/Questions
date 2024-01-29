package com.shivam.demo.model;

import jakarta.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(nullable = false)
    private String name ;

    @Column(nullable = false)
    private String link ;

    @Column(nullable = false)
    private String hint ;



    @Column(nullable = false)
    private int difficulty;

    @Column(nullable = false)
    private int importnace ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHint() {
        return hint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getImportnace() {
        return importnace;
    }

    public void setImportnace(int importnace) {
        this.importnace = importnace;
    }


}
