package com.myProject.service.command;

import com.myProject.dto.Report;
import com.myProject.entitie.User;
import com.myProject.service.CashierManager;
import com.myProject.service.employee.Employee;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static com.myProject.util.Constants.PATH;

public class ReportX implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ReportX.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("Starting preparing X report");
        CashierManager manager = (CashierManager) req.getServletContext().getAttribute("CashierManager");
        String seniorCashier = ((Employee) req.getSession()
                .getAttribute("employee"))
                .getUser()
                .getLogin();
        Report report = manager.createReport();
        report.setSeniorCashier(seniorCashier);
        req.getSession().setAttribute("report", report);
        return PATH + "report_x.jsp";
    }

}
