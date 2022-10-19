package com.myProject.service.command;

import com.myProject.entitie.Order;
import com.myProject.service.CashierManager;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static com.myProject.util.Constants.*;
/**
 * Implementation of MOVIES_COMMAND
 */
public class Movies implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(Movies.class);

    /**
     *
     * @return
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        logger.info("MOVIES_COMMAND executed");
        CashierManager manager =
                (CashierManager) req.getServletContext().getAttribute("CashierManager");
        HttpSession session = req.getSession();
        String strPage = req.getParameter("page");
        String direction = req.getParameter("direction");
        String operation;
        String commandName;
        if ("IN".equals(direction)) {
            operation = "incomes";
            commandName = INCOMES_COMMAND;
        } else {
            operation = "orders";
            commandName = ORDERS_COMMAND;
        }
        int currentPage = 1;
        if (strPage != null &&
                !"null".equals(strPage) &&
                commandName.equals(session.getAttribute("command_name"))) {
            currentPage = Integer.parseInt(strPage);
        }
        int pagesTotal = (int)Math. ceil(manager.findRowsTotal(direction)/10d);
        List<Order> list = manager.findAll((currentPage - 1) * 10, 10, direction);
        Map<String, Double> totals = manager.ordersTotals(direction);
        session.setAttribute("total_amount", totals.get("total_amount"));
        session.setAttribute("total_quantity", totals.get("total_quantity"));
        session.setAttribute("result", list);
        session.setAttribute("page", currentPage);
        session.setAttribute("pages_total", pagesTotal);
        session.setAttribute("command_name", commandName);
        session.setAttribute("operation", operation);
        session.setAttribute("title", "command." + operation);
        return PATH + "orders_list.jsp";
    }
}
