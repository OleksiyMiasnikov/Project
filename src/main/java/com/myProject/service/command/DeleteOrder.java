package com.myProject.service.command;

import com.myProject.service.CashierManager;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.myProject.util.Constants.ORDERS_COMMAND;

/**
 * Implementation of DELETE_ORDER_COMMAND
 */

public class DeleteOrder implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(DeleteOrder.class);

    /**
     * gets array of orders from 'order_list.jsp' and invokes method to delete these orders
     *
     * @return command ORDERS_COMMAND
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, ServletException, IOException {
        logger.info("DELETE_ORDER_COMMAND executed");
        String[] orders = req.getParameterMap().get("orders");
        if (orders != null) {
            CashierManager cashierManager =
                    (CashierManager) req.getServletContext().getAttribute("CashierManager");
            cashierManager.deleteAll(orders);
        }
        return "controller?command=" + ORDERS_COMMAND;
    }
}
