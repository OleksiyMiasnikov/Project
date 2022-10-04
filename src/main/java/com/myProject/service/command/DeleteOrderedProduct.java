package com.myProject.service.command;

import com.myProject.service.exception.DaoException;
import com.myProject.service.CashierManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.myProject.util.Constants.ORDERS_COMMAND;

public class DeleteOrderedProduct implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(DeleteOrderedProduct.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("execute ");
        String[] products = req.getParameterMap().get("products");
        String strId = req.getParameter("order_id");
        String operation = (String) req.getSession().getAttribute("operation");
        if (products != null && strId != null) {
            CashierManager cashierManager = (CashierManager) req.getServletContext().getAttribute("CashierManager");
            if (cashierManager.deleteProductsInOrder(strId, products)) {
                return "controller?command=command.orders";
            }
        }
        return "controller?command=command.serve_order&id=" +
                strId +
                "&operation=" +
                operation;
    }
}
