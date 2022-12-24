package com.myProject.service.command;

import com.myProject.employee.Employee;
import com.myProject.service.UserManager;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import com.myProject.entitie.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.myProject.util.Constants.*;

/**
 * Implementation of ADD_USER_COMMAND
 * prepares data for jsp page and passes control to 'user_details.jsp'
 */
public class NewUser implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(NewUser.class);

    /**
     * prepares data for jsp page
     *
     * @return address of 'user_details.jsp'
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        logger.info("ADD_USER_COMMAND executed");
        UserManager userManager = (UserManager) req.getServletContext().getAttribute("UserManager");
        HttpSession session = req.getSession();
        List<Role> rolesList = userManager.findAllRoles(0, 1000);
        Employee.menuUp(session, List.of(ADD_USER_DETAILS_COMMAND, BACK_COMMAND));
        session.setAttribute("roles", rolesList);
        session.setAttribute("title", "command.add_user");
        return PATH + "user_details.jsp";
    }
}
