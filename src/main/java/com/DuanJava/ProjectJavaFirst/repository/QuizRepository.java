package com.DuanJava.ProjectJavaFirst.repository;

import com.DuanJava.ProjectJavaFirst.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCourseId(Long courseId);
}
