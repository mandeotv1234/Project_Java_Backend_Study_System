package com.DuanJava.ProjectJavaFirst.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String category; // Loại khóa học (Frontend, Backend, Data Science,...)
    private String duration; // Thời gian học (Ví dụ: "8 tuần")

    private Double price; // Giá khóa học

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Thời gian tạo khóa học

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher; // Khóa học do giáo viên tạo

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // ✅ Giúp tránh loop
    private List<Lecture> lectures;  // 🔥 Thay thế Materials bằng Lecture
}
