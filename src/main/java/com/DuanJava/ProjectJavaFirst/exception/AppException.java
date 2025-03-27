package com.DuanJava.ProjectJavaFirst.exception;

public class AppException extends RuntimeException{
    private ErrorCode errorCode;

    public AppException( ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
