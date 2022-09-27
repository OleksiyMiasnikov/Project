package com.myProject.service;

import com.myProject.service.exception.DaoException;
import com.myProject.service.command.Receiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(Controller.class);


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = "error_page.jsp";
        String commandName = req.getParameter("command");
        logger.info(commandName);
        try {
            address = Receiver.runCommand(req, resp, commandName);
        } catch (DaoException e) {
            req.getSession().setAttribute("error_message", e.getMessage());
            req.getSession().setAttribute("error_cause", e.getCause().getMessage());
        }
        logger.info(address);
        String method = req.getMethod();
        if ("GET".equals(method)) {
            logger.info("GET");
            req.getRequestDispatcher(address).forward(req, resp);
        } else if ("POST".equals(method)) {
            logger.info("POST");
            resp.sendRedirect(address);
        }
    }

}
