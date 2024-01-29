package com.shivam.demo.repository;

import com.shivam.demo.model.UserQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface UserQuestionRepository extends JpaRepository<UserQuestion,Long> {
    List<UserQuestion> findByUserIdAndDateSolved(Long userId, LocalDate dateSolved);

    List<UserQuestion> findByUserId(Long userId);

    List<UserQuestion> findByUserIdAndQuestionIdAndDateSolved(Long userId, Long questionId, LocalDate dateSolved);


}
