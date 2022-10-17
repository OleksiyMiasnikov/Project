package com.myProject.service.exception;

import javax.mail.MessagingException;

public class AppException extends DaoException{
    public AppException(Throwable e) {
        super(e);
    }
}
