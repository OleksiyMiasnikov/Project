package com.myProject.service.command;

import com.myProject.dao.entitie.Role;
import com.myProject.service.exception.DaoException;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AddUser implements Command{
    private static final Logger logger = (Logger) LogManager.getLogger(AddUser.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        logger.info("--- AddUser ---");
        UserManager userManager = (UserManager) req.getServletContext().getAttribute("UserManager");
        List<Role> rolesList = userManager.findAllRoles(0, 1000);
        req.getSession().setAttribute("roles", rolesList);
        return"user_details.jsp";
    }
}
