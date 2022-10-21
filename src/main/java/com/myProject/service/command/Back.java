package com.myProject.service.command;

import com.myProject.employee.Employee;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.myProject.util.Constants.PATH;

/**
 * Implementation of BACK_COMMAND
 */
public class Back implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(Back.class);

    /**
     * pops list of menu items from stack and puts it into session attribute
     *
     * @return start command of current employee
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, AppException {
        logger.info("BACK_COMMAND executed");
        Employee employee = (Employee) req.getSession().getAttribute("employee");
        employee.popMenuItems();
        String result = employee.popPage();
        req.getSession().setAttribute("employee", employee);
        if ("controller?command=command.orders".equals(result)) {
            req.getSession().removeAttribute("pdf");
        }
        //return "controller?command=" + employee.getStartCommand();
        return result;
    }
}
