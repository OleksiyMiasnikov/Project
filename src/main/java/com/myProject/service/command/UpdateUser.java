package com.myProject.service.command;

import com.myProject.employee.Employee;
import com.myProject.service.exception.AppException;
import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import com.myProject.service.UserManager;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.myProject.util.Constants.*;

/**
 * Implementation of UPDATE_USER_COMMAND
 */

public class UpdateUser implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(UpdateUser.class);
/**
 *  prepares data for updating user by 'id' from request parameter 'selectedUser'
 *  @return address of jsp page 'user_details.jsp'
 */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        String userId = req.getParameter("selectedUser");
        logger.info("UPDATE_USER_COMMAND. Updating user with id: " + userId);
        HttpSession session = req.getSession();
        UserManager userManager = (UserManager) req.getServletContext().getAttribute("UserManager");
        User user = userManager.read(Long.parseLong(userId));
        List<Role> rolesList = userManager.findAllRoles(0, 1000);
        Employee.manuUp(session, List.of(ADD_USER_DETAILS_COMMAND, BACK_COMMAND));
        req.setAttribute("user", user);
        req.setAttribute("roles", rolesList);
        session.setAttribute("title", "command.update_user");
        return  PATH + "user_details.jsp";
    }
}
