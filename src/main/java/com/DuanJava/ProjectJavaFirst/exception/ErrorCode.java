package com.DuanJava.ProjectJavaFirst.exception;

public enum ErrorCode {
    USER_EXISTED(1001,"User existed"),

    USER_NOT_EXISTED(1002,"User is not existed"),
    UNAUTHENTICATED(1003,"User  unauthenticated");



    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private  int code;
    private  String message;

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
}
