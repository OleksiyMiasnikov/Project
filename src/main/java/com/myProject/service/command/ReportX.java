package com.myProject.service.command;

import com.myProject.dto.Report;
import com.myProject.service.CashierManager;
import com.myProject.service.employee.Employee;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.myProject.util.Constants.*;

public class ReportX implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ReportX.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("Starting preparing X report");
        CashierManager manager = (CashierManager) req.getServletContext().getAttribute("CashierManager");
        Employee employee = (Employee) req.getSession().getAttribute("employee");
        String seniorCashier = employee.getUser().getLogin();
        Report report = manager.createReport();
        report.setSeniorCashier(seniorCashier);
        employee.setMenuItems(new ArrayList<>(Arrays
                .asList(PRINT_REPORT_COMMAND,
                        SEND_REPORT_COMMAND,
                        BACK_COMMAND)));
        req.getSession().setAttribute("employee", employee);
        req.getSession().setAttribute("report", report);
        return PATH + "report_x.jsp";
    }

}
