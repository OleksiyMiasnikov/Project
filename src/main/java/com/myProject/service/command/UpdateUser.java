package com.myProject.service.command;

import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import com.myProject.service.UserManager;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.myProject.util.Constants.PATH;
/**
 * Implementation of
 */

public class UpdateUser implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(UpdateUser.class);
/**
 *  prepares data for updating user by 'id' from request parameter 'selectedUser'
 *  @return address of jsp page 'user_details.jsp'
 */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
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
