package com.DuanJava.ProjectJavaFirst.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerSubmissionDTO {
    private Long questionId;    // ID của câu hỏi
    private String chosenAnswer; // Đáp án mà học sinh chọn
}
