package com.myProject.service.command;

import com.myProject.service.exception.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.myProject.util.Constants.START_PAGE;
/**
 * Implementation of
 */
public class Logout implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        req.getSession().invalidate();
        return START_PAGE;
    }
}
