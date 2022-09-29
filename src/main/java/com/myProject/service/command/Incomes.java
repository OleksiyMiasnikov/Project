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

public class Incomes implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        CashierManager manager =
                (CashierManager) req.getSession().getServletContext().getAttribute("CashierManager");

        String strPage = req.getParameter("page");
        String direction = req.getParameter("direction");
        int currentPage = 1;
        if (strPage != null && !"".equals(strPage)) currentPage = Integer.parseInt(strPage);
        int pagesTotal = (int)Math. ceil(manager.findRowsTotal("IN")/10d);
        List<Order> list = manager.findAll((currentPage - 1) * 10, 10, "IN");
        Map<String, Double> totals = manager.OrdersTotals("IN");
        req.getSession().setAttribute("result", list);
        req.getSession().setAttribute("total_amount", totals.get("total_amount"));
        req.getSession().setAttribute("total_quantity", totals.get("total_quantity"));
        req.getSession().setAttribute("result", list);
        req.getSession().setAttribute("page", currentPage);
        req.getSession().setAttribute("pages_total", pagesTotal);
        req.getSession().setAttribute("command_name", INCOMES_COMMAND);
        req.getSession().setAttribute("operation", "incomes");
        return "orders_list.jsp";
    }
}
