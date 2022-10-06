package com.myProject.service.command;

import com.myProject.service.employee.Employee;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.myProject.util.Constants.PATH;

public class Reports implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(Reports.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("--- Reports ---");
        logger.info(req.getParameter("command"));
        String role = ((Employee) req.getSession().getAttribute("employee")).getUser().getRole().getName();
        if ("senior cashier".equals(role)) {
            return PATH + "reports_senior_cashier.jsp";
        }
        if ("commodity expert".equals(role)) {
            return PATH + "reports_commodity_expert.jsp";
        }
        return req.getHeader("referer");
    }
}
