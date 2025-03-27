package com.DuanJava.ProjectJavaFirst.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_submission_id", nullable = false)
    private QuizSubmission quizSubmission;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    private String chosenAnswer; // Đáp án mà học sinh chọn
}
