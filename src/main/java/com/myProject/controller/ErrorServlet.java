package com.myProject.controller;

import com.myProject.exception.DbException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/error")
public class ErrorServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(ErrorServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        logger.info("name=" + name);
        if ("error".equals(name)) {
            throw new NullPointerException("Null");
        }
        throw new ServletException(new DbException("DBException"));
    }
}
