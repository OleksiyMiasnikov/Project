package com.myProject.service.command;

import com.myProject.employee.Employee;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Back implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(Back.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("\"Back\" pressed");
        Employee employee = (Employee)req.getSession().getAttribute("employee");
        employee.popMenuItems();
        req.getSession().setAttribute("employee", employee);
        return "controller?command=" + employee.getStartCommand();
    }
}
