package com.myProject.service.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUser implements Command{
    private static final Logger logger = (Logger) LogManager.getLogger(AddUser.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("--- AddUser ---");
        try {
            req.getRequestDispatcher("WEB-INF/Pages/addUser.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
