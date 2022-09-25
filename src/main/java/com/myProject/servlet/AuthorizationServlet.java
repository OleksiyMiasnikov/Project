package com.myProject.servlet;

import com.myProject.dao.entitie.User;
import com.myProject.employee.Employee;
import com.myProject.exception.DaoException;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet("/authorization")
public class AuthorizationServlet extends HttpServlet {

    private static final Logger logger = (Logger) LogManager.getLogger(AuthorizationServlet.class);
/*
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        logger.info("login is \"" + login + "\".");
        try {
            User user = userManager.findUser(login);
            if (user != null && password.equals(user.getPassword())) {
                Employee employee = Employee.createEmployee(user);
                HttpSession session = req.getSession();
                session.setAttribute("Employee", employee);
                session.setAttribute("menuItems", employee.getMenuItems());
                logger.info("Session: "
                            + session.getId()
                            + ". Current user:"
                            + employee.getUser().getLogin()
                            + ". Role: "
                            + employee.getUser().getRole());
                employee.initWindow(req, resp);
            } else {
                resp.setContentType("text/html");
                PrintWriter writer = resp.getWriter();
                writer.println("<script type=\"text/javascript\">");
                writer.println("alert('Invalid Login or Password');");
                writer.println("</script>");
                req.getRequestDispatcher("index.html").include(req, resp);
            }
        } catch (DaoException e) {
            logger.error("LOG.error");
            e.printStackTrace();
        }
    }*/
}
