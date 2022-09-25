package com.myProject.service.command;

import com.myProject.dao.entitie.User;
import com.myProject.employee.Employee;
import com.myProject.exception.DaoException;
import com.myProject.service.UserManager;
import com.myProject.servlet.AuthorizationServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

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
            req.getSession().setAttribute("Employee", employee);
            logger.info(employee);
            return "jsp/main_window.jsp";
        } else {
            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();
            writer.println("<script type=\"text/javascript\">");
            writer.println("alert('Invalid Login or Password');");
            writer.println("</script>");
            req.getRequestDispatcher("index.html").include(req, resp);
            }

        return null;
    }
}
