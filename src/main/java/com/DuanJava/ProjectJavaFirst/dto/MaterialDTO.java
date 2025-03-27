package com.DuanJava.ProjectJavaFirst.dto;

import lombok.Data;

@Data
public class MaterialDTO {
    private String title;
    private String type; // "VIDEO" hoặc "PDF"
    private String url;
}
