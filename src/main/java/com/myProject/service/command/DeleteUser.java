package com.myProject.service.command;

import com.myProject.dao.entitie.User;
import com.myProject.exception.DaoException;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUser implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(DeleteUser.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        String userLogin = req.getParameter("users");
        if (userLogin == null || userLogin.equals("")) {
            try {
                req.getRequestDispatcher("jsp/userDetails.jsp").forward(req, resp);
            } catch (IOException | ServletException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        logger.info("Start deleting user: " + userLogin);
        UserManager userManager = (UserManager) req.getSession().getServletContext().getAttribute("UserManager");
        if (userManager.deleteUser(userLogin)) {
            logger.info("User: " + userLogin + "has been deleted");
        } else {
            logger.info("Unable to delete user: " + userLogin);
        }

        try {
            req.getRequestDispatcher("jsp/userDetails.jsp").forward(req, resp);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }

    }
}
