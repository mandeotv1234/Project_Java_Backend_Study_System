package com.DuanJava.ProjectJavaFirst.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    private Double score; // Điểm số của bài kiểm tra
    private LocalDateTime submittedAt; // Thời gian nộp bài

    @OneToMany(mappedBy = "quizSubmission", cascade = CascadeType.ALL)
    private List<AnswerSubmission> answers; // Danh sách câu trả lời của học sinh
}
