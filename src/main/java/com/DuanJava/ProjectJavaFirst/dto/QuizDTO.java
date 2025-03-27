package com.DuanJava.ProjectJavaFirst.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDTO {
    private Long id;
    private String title;
    private String description;
    private Double maxScore;
    private Long courseId;
    private List<QuestionDTO> questions;
}
