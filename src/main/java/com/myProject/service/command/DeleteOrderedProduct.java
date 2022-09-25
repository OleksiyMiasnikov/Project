package com.myProject.service.command;

import com.myProject.employee.Employee;
import com.myProject.exception.DaoException;
import com.myProject.service.CashierManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteOrderedProduct implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(DeleteOrderedProduct.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("execute ");
        String[] products = req.getParameterMap().get("products");
        String strId = req.getParameter("order_id");
        if (products != null && strId != null) {
            CashierManager cashierManager = (CashierManager) req.getServletContext().getAttribute("CashierManager");
            cashierManager.deleteProductsInOrder(strId, products);
        }
        logger.info("finished ");
        ((Employee)req.getSession().getAttribute("Employee")).initWindow(req, resp);
        return strId;
    }
}
