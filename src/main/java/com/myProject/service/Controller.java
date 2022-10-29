package com.myProject.service;

import com.myProject.employee.Employee;
import com.myProject.service.command.Receiver;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.myProject.util.Constants.BACK_COMMAND;
import static com.myProject.util.Constants.PATH;

/**
 * main servlet gets command name from jsp pages,
 * invokes the appropriate commands method
 * and passes control to the page at the address returned from the commands method
 * if an exception occurs control will pass to error_page.jsp
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(Controller.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = PATH + "error_page.jsp";
        String commandName = req.getParameter("command");
        logger.info("Command name: " + commandName);
        try {
            address = Receiver.runCommand(req, resp, commandName);
        } catch (DaoException | AppException e) {
            req.getSession().setAttribute("error_message", e.getMessage());
            req.getSession().setAttribute("title", "data_error");
            if (e.getCause() != null) {
                req.getSession().setAttribute("error_cause", e.getCause().getMessage());
            }
            Employee.manuUp(req.getSession(), List.of(BACK_COMMAND));
        }
        if (!"command.movies".equals(commandName)) {
            req.getSession().setAttribute("previous_command", commandName);
        }
        logger.info("Address: " + address);
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
