package com.myProject.service.command;

import com.myProject.service.employee.Employee;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReportZ implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ReportZ.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("Starting preparing Z report");
        logger.info(req.getParameter("command"));
        String role = ((Employee) req.getSession().getAttribute("employee")).getUser().getRole().getName();
        return req.getHeader("referer");
    }
}
