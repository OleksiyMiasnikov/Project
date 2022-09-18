package com.myProject.employee;

import com.myProject.dao.entitie.User;
import com.myProject.exception.DaoException;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.myProject.util.Constants.*;

public class Admin extends Employee{
    private static final Logger logger = (Logger) LogManager.getLogger(Admin.class);
    public Admin(User user) {
        super(user, COMMAND_ADD_USER, COMMAND_UPDATE_USER, COMMAND_DELETE_USER, COMMAND_CREATE_DATABASE);
    }

    @Override
    public void initWindow(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        logger.info("initWindow start");
        UserManager userManager = (UserManager) req.getSession().getServletContext().getAttribute("UserManager");
        List<User> userList = userManager.findAllUsers();
        req.setAttribute("result", userList);
        req.setAttribute("Fragment", "/AdminFragment");
        logger.info(Arrays.toString(userList.toArray()));
        try {
            req.getRequestDispatcher("jsp/mainWindow.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
