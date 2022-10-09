package com.myProject.service.command;

import com.myProject.service.employee.Employee;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.myProject.util.Constants.NEW_ORDER_COMMAND;

public class ReportZ implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ReportZ.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("Starting preparing Z report");
        return "controller?command=command.x_reports&z_report=YES";
    }
}
