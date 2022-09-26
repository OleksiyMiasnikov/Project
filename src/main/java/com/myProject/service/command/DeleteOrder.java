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

public class DeleteOrder implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(DeleteOrder.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("execute ");
        String[] orders = req.getParameterMap().get("orders");
        if (orders != null) {
            CashierManager cashierManager = (CashierManager) req.getServletContext().getAttribute("CashierManager");
            cashierManager.deleteAll(orders);
        }
        logger.info("finished ");
        return "controller?command=" + ORDERS_COMMAND;
    }
}
