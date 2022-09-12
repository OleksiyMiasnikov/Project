package com.myProject.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet("/menuCommand")
public class CommandServlet extends HttpServlet {

    private static final Logger logger = (Logger) LogManager.getLogger(CommandServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String button = req.getParameter("menuButton");
        logger.info(button + " was pressed");
        resp.sendRedirect("menu");
    }
}
