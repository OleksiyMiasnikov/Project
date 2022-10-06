package com.myProject.service.command;

import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import com.myProject.service.UserManager;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUserDetails implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(AddUserDetails.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("started");
        long id = 0L;
        String login = req.getParameter("newLogin");
        String password = req.getParameter("newPassword");
        String email = req.getParameter("newEmail");
        String role = req.getParameter("newRole");
        String strId = req.getParameter("id");
        logger.info(role);
        if (strId != null && !strId.equals("")) id = Long.parseLong(strId);
        UserManager userManager = (UserManager) req.getSession().getServletContext().getAttribute("UserManager");
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
        return "controller?command=command.show_users";
    }
}
