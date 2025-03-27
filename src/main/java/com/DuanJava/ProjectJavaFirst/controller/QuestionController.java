package com.DuanJava.ProjectJavaFirst.controller;

import com.DuanJava.ProjectJavaFirst.dto.QuestionDTO;
import com.DuanJava.ProjectJavaFirst.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    // ✅ 1. Thêm câu hỏi vào bài kiểm tra
    @PostMapping("/add")
    public ResponseEntity<QuestionDTO> addQuestion(@RequestBody QuestionDTO questionDTO) {
        return ResponseEntity.ok(questionService.addQuestionToQuiz(questionDTO));
    }

    // ✅ 2. Lấy danh sách câu hỏi theo bài kiểm tra
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByQuiz(@PathVariable Long quizId) {
        return ResponseEntity.ok(questionService.getQuestionsByQuiz(quizId));
    }
}
