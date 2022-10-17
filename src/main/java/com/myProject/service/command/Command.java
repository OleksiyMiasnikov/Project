package com.myProject.service.command;

import com.myProject.service.exception.DaoException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * basic interface for pattern command
 */
public interface Command {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException;
}
