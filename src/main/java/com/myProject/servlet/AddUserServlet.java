package com.myProject.servlet;

import com.myProject.dao.entitie.Role;
import com.myProject.dao.entitie.User;
import com.myProject.employee.Employee;
import com.myProject.exception.DaoException;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.myProject.util.Constants.COMMAND_SHOW_USERS;

@WebServlet("/serveUser")
public class AddUserServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(AddUserServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.info("started");
        if ("Cancel".equals(req.getParameter("button"))) {
            resp.sendRedirect("controller?command=" + COMMAND_SHOW_USERS);
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
            UserManager userManager = (UserManager) getServletContext().getAttribute("UserManager");
            User newUser = new User(id, login, password, email, new Role(userManager.getIdRole(role), role));
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
            req.getRequestDispatcher("controller?command=" + COMMAND_SHOW_USERS).forward(req, resp);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
}
