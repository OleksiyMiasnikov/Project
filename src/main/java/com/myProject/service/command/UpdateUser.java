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

/**
 *  prepares data for updating user by 'id' from request parameter 'selectedUser'
 *  @return address of jsp page 'user_details.jsp'
 */
public class UpdateUser implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(UpdateUser.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        UserManager userManager = (UserManager) req.getServletContext().getAttribute("UserManager");
        String userId = req.getParameter("selectedUser");
        logger.info("Start updating user: " + userId);
        User user = userManager.read(Long.parseLong(userId));
        List<Role> rolesList = userManager.findAllRoles(0, 1000);
        req.setAttribute("user", user);
        req.setAttribute("roles", rolesList);
        req.getSession().setAttribute("title", "command.update_user");
        return  PATH + "user_details.jsp";
    }
}
