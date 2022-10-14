package com.myProject.service.command;

import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.myProject.util.Constants.START_PAGE;

/**
 * Implementation of LOGOUT_COMMAND
 */
public class Logout implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(Logout.class);

    /**
     * invalidates current session and passes control to login page
     *
     * @return address of login page
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, ServletException, IOException {
        logger.info("LOGOUT_COMMAND executed");
        req.getSession().invalidate();
        return START_PAGE;
    }
}
