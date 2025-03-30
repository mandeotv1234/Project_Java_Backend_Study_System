package com.DuanJava.ProjectJavaFirst.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private int code;
    private String message;
    private Object result;

    public ApiResponse() {
        this.code = 1000; // Mặc định là thành công
    }

    public ApiResponse(String message, Object result) {
        this.code = 1000;
        this.message = message;
        this.result = result;
    }

    public ApiResponse(int code, String message, Object result) { // ✅ Thêm constructor 3 tham số
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public ApiResponse(int code, String message) { // ✅ Thêm constructor 3 tham số
        this.code = code;
        this.message = message;
        this.result = null;
    }

    // Getters và Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
