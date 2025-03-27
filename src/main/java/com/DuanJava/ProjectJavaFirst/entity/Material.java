package com.DuanJava.ProjectJavaFirst.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String type;
    private String url;

    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    @JsonIgnore
    private Lecture lecture;  // 🔥 Mỗi tài liệu thuộc về một bài giảng
}
