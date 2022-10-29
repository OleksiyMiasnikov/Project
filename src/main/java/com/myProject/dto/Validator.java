package com.myProject.dto;

import com.myProject.service.exception.AppException;

public interface Validator <T> {
    T isValid() throws AppException;
}
