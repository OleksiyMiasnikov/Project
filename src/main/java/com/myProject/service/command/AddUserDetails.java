package com.myProject.service.command;

import com.myProject.dto.UserDto;
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
        String password;
        if ("YES".equals(req.getParameter("isPasswordChanged"))) {
            password = EncryptPassword.encrypt(req.getParameter("newPassword"));
        } else {
            password = req.getParameter("oldPassword");
        }
        UserDto userDto = UserDto.builder()
                .strId(req.getParameter("id"))
                .login(req.getParameter("newLogin"))
                .password(password)
                .email(req.getParameter("newEmail"))
                .role(req.getParameter("newRole"))
                .build()
                .isValid();
        User newUser = User.builder()
                .id(userDto.getId())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .role(Role.builder()
                        .id(userManager.getIdRole(userDto.getRole()))
                        .name(userDto.getRole())
                        .build())
                .build();
        if (newUser.getId() == 0L) {
            if (userManager.addUser(newUser) != null) {
                logger.info(newUser.getLogin() + " added");
            } else {
                logger.info("Unable to add user " + newUser.getLogin());
            }
        } else {
            userManager.updateUser(newUser);
            logger.info(newUser.getLogin() + " updated");
        }
        return "controller?command=command.back";
    }
}
