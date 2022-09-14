package com.myProject.controller;

import com.myProject.dao.entitie.Role;
import com.myProject.dao.entitie.User;
import com.myProject.exception.DbException;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(AddUserServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if ("Cancel".equals(req.getParameter("button"))) {
            try {
                logger.info("'Cansel' has pressed");
                resp.sendRedirect("main");
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        long id = 0L;
        String login = req.getParameter("newLogin");
        String password = req.getParameter("newPassword");
        String email = req.getParameter("newEmail");
        String role = req.getParameter("newRole");
        String strId = req.getParameter("id");
        if (strId != null && strId != "") id = Long.parseLong(strId);
        try {
            UserManager userManager = (UserManager) req.getSession().getServletContext().getAttribute("UserManager");
            User newUser = new User(login, password, email, new Role(userManager.getIdRole(role), role));
            if (id == 0L) {
                if (userManager.addUser(newUser)){
                    logger.info(login + " added");
                } else {
                    logger.info("Unable to add user " + login);
                }
            } else {
                newUser.setId(id);
                userManager.updateUser(newUser);
                logger.info(login + " updated");
            }
            resp.sendRedirect("main");
        } catch (DbException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
