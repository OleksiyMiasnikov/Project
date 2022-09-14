package com.myProject.service.command;

import com.myProject.dao.entitie.User;
import com.myProject.exception.DbException;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DeleteUser implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(DeleteUser.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws DbException {
        String userLogin = req.getParameter("users");
        logger.info("Start deleting user: " + userLogin);
        UserManager userManager = (UserManager) req.getSession().getServletContext().getAttribute("UserManager");
        User deletedUser = userManager.deleteUser(userLogin);
        logger.info("User: " + deletedUser.getLogin() + "has been deleted");
        try {
            resp.sendRedirect("main");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
