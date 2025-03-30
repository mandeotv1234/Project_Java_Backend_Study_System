package com.DuanJava.ProjectJavaFirst.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content; // Nội dung câu hỏi
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer; // Đáp án đúng (A, B, C, D)

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonIgnore // Ngăn vòng lặp khi serialize
    private Quiz quiz;
}
