package com.myProject.service.command;

import com.myProject.employee.Employee;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static com.myProject.util.Constants.*;
import static com.myProject.util.Constants.BACK_COMMAND;

/**
 * Implementation of PREPARING_EMAIL_COMMAND
 */
public class PreparingEmail implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(PreparingEmail.class);

    /**
     * prepares data for jsp page
     *
     * @return address of 'send_report.jsp'
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        logger.info("PREPARING_EMAIL_COMMAND executed");
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        session.setAttribute("from_address_mail", "oleksiymiasnikov@gmail.com");
        session.setAttribute("to_address_mail", employee.getUser().getEmail());
        session.setAttribute("subject_mail", "Report");
        employee.setMenuItems(List.of(SEND_EMAIL_COMMAND, BACK_COMMAND));
        employee.setStackOfPages((String) session.getAttribute("previous_command"));
        session.setAttribute("employee", employee);
        return PATH + "send_report.jsp";
    }
}
