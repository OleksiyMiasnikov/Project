package com.myProject.servlet;

import com.myProject.dao.entitie.Role;
import com.myProject.dao.entitie.User;
import com.myProject.employee.Employee;
import com.myProject.exception.DaoException;
import com.myProject.service.RoleManager;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/serveUser")
public class AddUserServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(AddUserServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("started");
        if ("Cancel".equals(req.getParameter("button"))) {
            try {
                logger.info("'Cansel' has pressed");
                ((Employee) req.getSession().getAttribute("Employee")).initWindow(req, resp);
                return;
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        }
        long id = 0L;
        String login = req.getParameter("newLogin");
        String password = req.getParameter("newPassword");
        String email = req.getParameter("newEmail");
        String role = req.getParameter("newRole");
        String strId = req.getParameter("id");
        logger.info(role);
        if (strId != null && !strId.equals("")) id = Long.parseLong(strId);
        try {
            UserManager userManager = (UserManager) req.getSession().getServletContext().getAttribute("UserManager");
            RoleManager roleManager = (RoleManager) req.getSession().getServletContext().getAttribute("RoleManager");
            User newUser = new User(id, login, password, email, new Role(roleManager.getIdRole(role), role));
            logger.info(newUser);
            if (id == 0L) {
                if (userManager.addUser(newUser) != null){
                    logger.info(login + " added");
                } else {
                    logger.info("Unable to add user " + login);
                }
            } else {
                userManager.updateUser(newUser);
                logger.info(login + " updated");
            }
            ((Employee) req.getSession().getAttribute("Employee")).initWindow(req, resp);
            //resp.sendRedirect("main");
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
}
