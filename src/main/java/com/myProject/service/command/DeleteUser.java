package com.myProject.service.command;

import com.myProject.exception.DaoException;
import com.myProject.service.CashierManager;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.myProject.util.Constants.COMMAND_SHOW_USERS;

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
        return "controller?command=" + COMMAND_SHOW_USERS;
    }
}
