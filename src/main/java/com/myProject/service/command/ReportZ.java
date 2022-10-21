package com.myProject.service.command;

import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Implementation of Z_REPORT_COMMAND
 */
public class ReportZ implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ReportZ.class);

    /**
     * pass control to X_REPORT_COMMAND with parameter z_report=YES
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        logger.info("Z_REPORT_COMMAND started");
        return "controller?command=command.x_reports&z_report=YES";
    }
}
