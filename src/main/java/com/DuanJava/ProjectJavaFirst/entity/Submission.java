package com.DuanJava.ProjectJavaFirst.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileUrl; // Link tới file bài nộp
    private String comments; // Ghi chú từ sinh viên hoặc giảng viên
    private Double grade; // Điểm số

    private LocalDateTime submittedAt; // Thời gian nộp bài

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
}
