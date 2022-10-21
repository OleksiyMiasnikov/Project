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
 * Implementation of SEND_REPORT_COMMAND
 */
public class SendReport implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(SendReport.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        logger.info("SEND_REPORT_COMMAND executed");
        Employee employee = (Employee) req.getSession().getAttribute("employee");
        req.getSession().setAttribute("from_address", "oleksiymiasnikov@gmail.com");
        req.getSession().setAttribute("to_address", employee.getUser().getEmail());
        return PATH + "send_report.jsp";
    }
}
