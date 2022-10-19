package com.myProject.service.command;

import com.myProject.entitie.User;
import com.myProject.service.UserManager;
import com.myProject.employee.Employee;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import com.myProject.util.EncryptPassword;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.myProject.util.Constants.START_PAGE;
/**
 * Implementation of AUTHORIZATION_COMMAND
 * gets user data from login page and checks if such user exist
 */
public class Authorization implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(Authorization.class);

    /**
     * Gets 'login' and 'password' from login page (index.jsp) parameters and finds user in database
     * if user exists creates employee by this user end puts it into session
     * else backs control to login page with error message.
     *
     * @return start command of authorized employee
     * or address of login page if employee has not been authorized
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, AppException {
        logger.info("AUTHORIZATION_COMMAND executed");
        UserManager userManager = (UserManager) req.getServletContext().getAttribute("UserManager");
        String login = req.getParameter("login");
        String password = EncryptPassword.encrypt(req.getParameter("password"));
        logger.info("login is \"" + login + "\".");
        User user = userManager.findUser(login);
        if (user != null && password.equals(user.getPassword())) {
            Employee employee = Employee.createEmployee(user);
            logger.info(employee);
            req.getSession().setAttribute("employee", employee);
            req.getSession().setAttribute("incorrectUser", "");
            return "controller?command=" + employee.getStartCommand();
        } else {
            req.getSession().setAttribute("incorrectUser","index_jsp.error");
            req.getSession().setAttribute("title", "error_page_jsp");
            return START_PAGE;
        }
    }

}


