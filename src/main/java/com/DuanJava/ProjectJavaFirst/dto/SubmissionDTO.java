package com.DuanJava.ProjectJavaFirst.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionDTO {
    private Long id;
    private String fileUrl;
    private String comments;
    private Double grade;
    private LocalDateTime submittedAt;
    private Long assignmentId;
    private Long studentId;
}
