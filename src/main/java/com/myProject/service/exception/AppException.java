package com.myProject.service.exception;

public class AppException extends Exception{
    public AppException(String message, Throwable e) {
        super(message, e);
    }
    public AppException(Throwable e) {
        super(e);
    }
}
