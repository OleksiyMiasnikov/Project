package com.myProject.validator;

import com.myProject.service.exception.AppException;

public interface Validator <T> {
    T isValid() throws AppException;
}
