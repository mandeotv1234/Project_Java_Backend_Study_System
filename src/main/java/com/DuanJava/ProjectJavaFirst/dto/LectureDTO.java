package com.DuanJava.ProjectJavaFirst.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LectureDTO {
    private String title;
    private String description;
    private LocalDateTime createdAt = LocalDateTime.now(); // Thời gian tạo khóa học

    private List<MaterialDTO> materials; // ✅ Danh sách tài liệu của bài giảng
}
