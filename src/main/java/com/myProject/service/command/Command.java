package com.myProject.service.command;

import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * basic interface for pattern command
 */
public interface Command {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException;
}
