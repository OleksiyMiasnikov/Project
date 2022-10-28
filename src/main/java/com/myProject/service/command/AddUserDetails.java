package com.myProject.service.command;

import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import com.myProject.service.UserManager;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import com.myProject.util.EncryptPassword;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implementation of ADD_USER_DETAILS_COMMAND
 */
public class AddUserDetails implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(AddUserDetails.class);

    /**
     * takes user details data from 'user_details.jsp' page
     * create new user if 'id' equals '0' else update user by 'id'
     *
     * @return command SHOW_USERS_COMMAND
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, AppException {
        logger.info("ADD_USER_DETAILS_COMMAND executed");
        UserManager userManager =
                (UserManager) req.getServletContext().getAttribute("UserManager");
        String login = req.getParameter("newLogin");
        String email = req.getParameter("newEmail");
        String role = req.getParameter("newRole");
        String strId = req.getParameter("id");
        String password = req.getParameter("oldPassword");
        if ("YES".equals(req.getParameter("isPasswordChanged"))) {
            password = EncryptPassword.encrypt(req.getParameter("newPassword"));
        }
        long id = 0L;
        if (strId != null && !strId.equals("")) {
            id = Long.parseLong(strId);
        }
        User newUser = User.builder()
                .id(id)
                .login(login)
                .password(password)
                .email(email)
                .role(Role.builder()
                        .id(userManager.getIdRole(role))
                        .name(role)
                        .build())
                .build();
        if (id == 0L) {
            if (userManager.addUser(newUser) != null) {
                logger.info(login + " added");
            } else {
                logger.info("Unable to add user " + login);
            }
        } else {
            userManager.updateUser(newUser);
            logger.info(login + " updated");
        }
        return "controller?command=command.back";
    }
}
