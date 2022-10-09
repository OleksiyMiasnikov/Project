package com.myProject.service.command;

import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import com.myProject.service.exception.DaoException;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.myProject.util.Constants.PATH;


public class UpdateUser implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(UpdateUser.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        String userId = req.getParameter("selectedUser");
        logger.info("Start updating user: " + userId);
        UserManager userManager = (UserManager) req.getSession().getServletContext().getAttribute("UserManager");
        User user = userManager.read(Long.parseLong(userId));
        req.setAttribute("user", user);
        List<Role> rolesList = userManager.findAllRoles(0, 1000);
        req.setAttribute("roles", rolesList);
        req.getSession().setAttribute("title", "command.update_user");
        return  PATH + "user_details.jsp";
    }
}
