package com.myProject.controller;

import com.myProject.dao.entitie.Role;
import com.myProject.dao.entitie.User;
import com.myProject.exception.DbException;
import com.myProject.service.UserManager;
import com.myProject.service.command.Receiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
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
        String login = req.getParameter("newLogin");
        String password = req.getParameter("newPassword");
        String email = req.getParameter("newEmail");
        String role = req.getParameter("newRole");
        User newUser = new User(login, password, email, new Role(0, role));
        UserManager userManager = (UserManager) req.getSession().getServletContext().getAttribute("UserManager");
        try {
            if (userManager.addUser(newUser)){
                logger.info(login + " added");
            } else {
                logger.info("Unable to add user " + login);
            }
            req.getRequestDispatcher("WEB-INF/Pages/mainWindow.jsp").forward(req, resp);
        } catch (DbException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
