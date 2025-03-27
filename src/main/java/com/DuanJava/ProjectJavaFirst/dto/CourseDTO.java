package com.DuanJava.ProjectJavaFirst.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CourseDTO {
    private String title;
    private String description;
    private String category; // Loại khóa học
    private String duration; // Thời gian học
    private Double price;
    private Long teacherId;
    private LocalDateTime createdAt = LocalDateTime.now(); // Thời gian tạo khóa học
    private List<LectureDTO> lectures; // ✅ Danh sách bài giảng
}
