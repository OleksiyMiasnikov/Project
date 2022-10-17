package com.myProject.service.exception;

public class AppException extends DaoException{
    public AppException(String message, Throwable e) {
        super(message, e);
    }
    public AppException(Throwable e) {
        super(e);
    }
}
