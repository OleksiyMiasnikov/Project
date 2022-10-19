package com.myProject.service.command;

import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.myProject.util.Constants.*;
/**
 * Implementation of ORDERS_COMMAND
 */
public class Orders implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(Orders.class);

    /**
     * passes control to MOVIES_COMMAND with parameter 'direction' equal 'OUT' and resends parameter 'page'
     *
     * @return command MOVIES_COMMAND with appropriate parameters
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, AppException {
        logger.info("ORDERS_COMMAND executed");
        return "controller?command=" + MOVIES_COMMAND + "&direction=OUT&page=" + req.getParameter("page");
    }
}
