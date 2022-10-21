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
import static com.myProject.util.Constants.BACK_COMMAND;

/**
 * Implementation of SEND_REPORT_COMMAND
 */
public class FillEmail implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(FillEmail.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        logger.info("SEND_REPORT_COMMAND executed");
        Employee employee = (Employee) req.getSession().getAttribute("employee");
        req.getSession().setAttribute("from_address_mail", "oleksiymiasnikov@gmail.com");
        req.getSession().setAttribute("to_address_mail", employee.getUser().getEmail());
        req.getSession().setAttribute("subject_mail", "Report");
        employee.setMenuItems(List.of(SEND_EMAIL_COMMAND, BACK_COMMAND));
        employee.setStackOfPages(req.getHeader("referer"));
        req.getSession().setAttribute("employee", employee);
        return PATH + "send_report.jsp";
    }
}
