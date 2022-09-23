package com.myProject.service.command;

import com.myProject.employee.Employee;
import com.myProject.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteOrderedProduct implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(DeleteOrderedProduct.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("execute ");
        String[] products = req.getParameterMap().get("products");
        String product = req.getParameter("products");
        String prod = req.getParameter("button");
        String produc = req.getParameter("menuButton");
        if (products != null) {
            for (String element: products) System.out.println(element);
            //CashierManager cashierManager = (CashierManager) req.getServletContext().getAttribute("CashierManager");
            //cashierManager.deleteAll(orders);
        }
        logger.info("finished ");
        ((Employee)req.getSession().getAttribute("Employee")).initWindow(req, resp);
    }
}
