package com.myProject.service;

import com.myProject.exception.DaoException;
import com.myProject.service.command.Receiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = "error_page.jsp";
        String commandName = req.getParameter("command");
        logger.info(commandName);
        try {
            address = Receiver.runCommand(req, resp, commandName);
        } catch (DaoException e) {
            req.getSession().setAttribute("error", e);
        }
        logger.info(address);
        req.getRequestDispatcher(address).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = "error_page.jsp";
        String commandName = req.getParameter("command");
        logger.info(commandName);
        try {
            address = Receiver.runCommand(req, resp, commandName);
        } catch (DaoException e) {
            req.getSession().setAttribute("error", e);
        }
        logger.info(address);
        resp.sendRedirect(address);
    }
}
