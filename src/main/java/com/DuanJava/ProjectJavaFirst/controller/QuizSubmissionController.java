package com.DuanJava.ProjectJavaFirst.controller;

import com.DuanJava.ProjectJavaFirst.dto.AnswerSubmissionDTO;
import com.DuanJava.ProjectJavaFirst.dto.QuizSubmissionDTO;
import com.DuanJava.ProjectJavaFirst.service.QuizSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-submissions")
@RequiredArgsConstructor
public class QuizSubmissionController {
    private final QuizSubmissionService quizSubmissionService;

    @PostMapping("/{quizId}/submit/{studentId}")
    public ResponseEntity<QuizSubmissionDTO> submitQuiz(
            @PathVariable Long quizId,
            @PathVariable Long studentId,
            @RequestBody List<AnswerSubmissionDTO> answers) {
        return ResponseEntity.ok(quizSubmissionService.submitQuiz(quizId, studentId, answers));
    }
}
