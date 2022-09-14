package com.myProject.service.command;

import com.myProject.dao.entitie.Role;
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

public class AddUser implements Command{
    private static final Logger logger = (Logger) LogManager.getLogger(AddUser.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("--- AddUser ---");
        UserManager userManager = (UserManager) req.getSession().getServletContext().getAttribute("UserManager");
        try {
            List<Role> rolesList = userManager.findAllRoles();
            req.setAttribute("roles", rolesList);
            req.getRequestDispatcher("Jsp/userDetails.jsp").forward(req, resp);
        } catch (ServletException | DbException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
