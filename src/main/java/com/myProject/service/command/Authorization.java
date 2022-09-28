package com.myProject.service.command;

import com.myProject.dao.entitie.User;
import com.myProject.service.UserManager;
import com.myProject.service.employee.Employee;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.myProject.util.Constants.START_PAGE;


public class Authorization implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(Authorization.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        logger.info("login is \"" + login + "\".");
        UserManager userManager = (UserManager) req.getServletContext().getAttribute("UserManager");
        User user = userManager.findUser(login);
        if (user != null && password.equals(user.getPassword())) {
            Employee employee = Employee.createEmployee(user);
            req.getSession().setAttribute("employee", employee);
            logger.info(employee);
            req.getSession().setAttribute("incorrectUser", "");
            return "controller?command=" + employee.getStartCommand();
        } else {
            req.getSession().setAttribute("incorrectUser",
                    "ERROR!  Incorrect login or password!");
            return START_PAGE;
        }
    }
}
