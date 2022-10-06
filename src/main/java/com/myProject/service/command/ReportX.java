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
import java.util.Arrays;
import java.util.List;

import static com.myProject.util.Constants.PATH;

public class ReportX implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ReportX.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("Starting preparing X report");
        User user = ((Employee) req.getSession().getAttribute("employee")).getUser();
        CashierManager manager = (CashierManager) req.getServletContext().getAttribute("CashierManager");
        List<Report> list = manager.createReport();
        //System.out.println(Arrays.asList(list));
        req.getSession().setAttribute("reports", list);
        return PATH + "report_x.jsp";
    }

}
