package com.myProject.service.command;

import com.myProject.employee.Employee;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.myProject.util.Constants.*;
import static com.myProject.util.Constants.CANCEL_ORDER_COMMAND;

/**
 * Implementation of NEW_INCOME_COMMAND
 */
public class NewIncome implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(NewIncome.class);

    /**
     * passes control to NEW_MOVIES_COMMAND with parameter 'direction' equal 'IN' and resends parameter 'page'
     *
     * @return command NEW_MOVIES_COMMAND with appropriate parameters
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        logger.info("NEW_INCOME_COMMAND executed");
        Employee.menuUp(req.getSession(), List.of(COMPLETE_ORDER_COMMAND, CANCEL_ORDER_COMMAND));
        return "controller?command=" + NEW_MOVIES_COMMAND + "&direction=IN&page=" + req.getParameter("page");
    }
}
