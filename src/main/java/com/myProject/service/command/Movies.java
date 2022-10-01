package com.myProject.service.command;

import com.myProject.dao.entitie.Order;
import com.myProject.service.CashierManager;
import com.myProject.service.exception.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.myProject.util.Constants.*;

public class Movies implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        CashierManager manager =
                (CashierManager) req.getSession().getServletContext().getAttribute("CashierManager");

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
                commandName.equals(req.getSession().getAttribute("command_name"))) {
            currentPage = Integer.parseInt(strPage);
        }
        int pagesTotal = (int)Math. ceil(manager.findRowsTotal(direction)/10d);
        List<Order> list = manager.findAll((currentPage - 1) * 10, 10, direction);
        Map<String, Double> totals = manager.ordersTotals(direction);
        req.getSession().setAttribute("result", list);
        req.getSession().setAttribute("total_amount", totals.get("total_amount"));
        req.getSession().setAttribute("total_quantity", totals.get("total_quantity"));
        req.getSession().setAttribute("result", list);
        req.getSession().setAttribute("page", currentPage);
        req.getSession().setAttribute("pages_total", pagesTotal);
        req.getSession().setAttribute("command_name", commandName);
        req.getSession().setAttribute("operation", operation);
        return PATH + "orders_list.jsp";
    }
}
