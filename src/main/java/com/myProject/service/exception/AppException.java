package com.myProject.service.exception;

public class AppException extends Exception{
    public AppException(Throwable e) {
        super(e);
    }
    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message) {
        super(message);
    }

}
