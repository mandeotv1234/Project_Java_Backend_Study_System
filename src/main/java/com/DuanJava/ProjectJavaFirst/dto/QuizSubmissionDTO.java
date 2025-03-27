package com.DuanJava.ProjectJavaFirst.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSubmissionDTO {
    private Long id;          // ID của bài nộp
    private Long quizId;      // ID bài kiểm tra
    private Long studentId;   // ID sinh viên nộp bài
    private Double score;     // Điểm số sau khi chấm bài
    private LocalDateTime submittedAt; // Thời gian nộp bài
}
