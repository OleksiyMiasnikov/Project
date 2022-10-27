package com.myProject.service.command;

import com.myProject.service.CashierManager;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implementation of DELETE_ORDER_PRODUCT_COMMAND
 */

public class DeleteOrderedProduct implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(DeleteOrderedProduct.class);

    /**
     * gets array of order details ids from 'order.jsp' and invokes method to delete these order details
     *
     * @return 'order.jsp' to show this order if there are some order details in current order
     * else command ORDERS_COMMAND
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, AppException {
        logger.info("DELETE_ORDER_PRODUCT_COMMAND executed");
        String strId = req.getParameter("order_id");
        String operation = (String) req.getSession().getAttribute("operation");
        String[] products = req.getParameterMap().get("products");
        if (products != null && strId != null) {
            CashierManager cashierManager =
                    (CashierManager) req.getServletContext().getAttribute("CashierManager");
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
