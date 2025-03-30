package com.DuanJava.ProjectJavaFirst.controller;

import com.DuanJava.ProjectJavaFirst.dto.QuizDTO;
import com.DuanJava.ProjectJavaFirst.entity.Course;
import com.DuanJava.ProjectJavaFirst.entity.Quiz;
import com.DuanJava.ProjectJavaFirst.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    // ✅ 1. Tạo bài kiểm tra
    @PostMapping("/create")
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizDTO quizDTO) {
        return ResponseEntity.ok(quizService.createQuiz(quizDTO));
    }

    // ✅ 2. Lấy bài kiểm tra theo ID
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizService.getQuizById(quizId));
    }
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Quiz>> getAllQuizzesByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(quizService.getAllQuizzesByCourseId(courseId));
    }
}
