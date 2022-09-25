package com.myProject.service.command;

import com.myProject.dao.entitie.Role;
import com.myProject.dao.entitie.User;
import com.myProject.exception.DaoException;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class UpdateUser implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(UpdateUser.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        String userLogin = req.getParameter("users");
        logger.info("Start updating user: " + userLogin);
        UserManager userManager = (UserManager) req.getSession().getServletContext().getAttribute("UserManager");
        User user = userManager.findUser(userLogin);
        req.setAttribute("user", user);
        List<Role> rolesList = userManager.findAllRoles();
        req.setAttribute("roles", rolesList);
        try {
            req.getRequestDispatcher("jsp/userDetails.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
