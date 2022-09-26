package com.myProject.service.command;

import com.myProject.service.exception.DaoException;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.myProject.util.Constants.SHOW_USERS_COMMAND;

public class DeleteUser implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(DeleteUser.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        logger.info("execute");
        String[] users = req.getParameterMap().get("users");
        if (users != null) {
            UserManager userManager = (UserManager) req.getServletContext().getAttribute("UserManager");
            userManager.deleteAll(users);
        }
        return "controller?command=" + SHOW_USERS_COMMAND;
    }
}
