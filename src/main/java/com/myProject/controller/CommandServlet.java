package com.myProject.controller;

import com.myProject.exception.DbException;
import com.myProject.service.command.Receiver;
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
        if ("Log out".equals(button)) {
            req.getSession().invalidate();
            req.getRequestDispatcher("index.html").forward(req, resp);
            return;
        }
        Receiver receiver = new Receiver();
        try {
            receiver.runCommand(req, resp, button);
        } catch (DbException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
