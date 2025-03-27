package com.DuanJava.ProjectJavaFirst.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignmentDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Long courseId; // Khóa học liên kết
}
