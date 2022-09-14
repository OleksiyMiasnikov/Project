package com.myProject.controller;

import com.myProject.dao.entitie.User;
import com.myProject.employee.Employee;
import com.myProject.exception.DbException;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/authorization")
public class AuthorizationServlet extends HttpServlet {

    private static final Logger logger = (Logger) LogManager.getLogger(AuthorizationServlet.class);

    private UserManager userManager;
    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        userManager = (UserManager) servletContext.getAttribute("UserManager");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        logger.info("login is \"" + login + "\".");
        try {
            User user = userManager.findUser(login);
            if (user != null && password.equals(user.getPassword())) {
                HttpSession session = req.getSession();
                if (session.isNew()) {
                    logger.info("New session: " + session.getId());
                } else {
                    logger.info("Existing session: " + session.getId());
                }
                Employee employee = Employee.createEmployee(user);
                session.setAttribute("Employee", employee);
                logger.info("Session: "
                            + session.getId()
                            + ". Current user:"
                            + employee.getUser().getLogin()
                            + ". Role: "
                            + employee.getUser().getRole());
                resp.sendRedirect("main");
            } else {
                resp.setContentType("text/html");
                PrintWriter writer = resp.getWriter();
                writer.println("<script type=\"text/javascript\">");
                writer.println("alert('Invalid Login or Password');");
                writer.println("</script>");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.html");
                requestDispatcher.include(req, resp);
            }
        } catch (DbException e) {
            logger.error("LOG.error");
            e.printStackTrace();
        }
    }
}
