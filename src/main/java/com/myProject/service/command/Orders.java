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

public class Orders implements Command {
    private double amountTotal;
    private double ordersTotal;
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        CashierManager manager =
                (CashierManager) req.getSession().getServletContext().getAttribute("CashierManager");

        String strPage = req.getParameter("page");
        String direction = req.getParameter("direction");
        int currentPage = 1;
        if (strPage != null && !"".equals(strPage)) currentPage = Integer.parseInt(strPage);
        int pagesTotal = (int)Math. ceil(manager.findRowsTotal("OUT")/10d);
        List<Order> list = manager.findAll((currentPage - 1) * 10, 10, "OUT");
        Map<String, Double> totals = manager.OrdersTotals("OUT");
        req.getSession().setAttribute("result", list);
        req.getSession().setAttribute("total_amount", totals.get("total_amount"));
        req.getSession().setAttribute("total_quantity", totals.get("total_quantity"));
        req.getSession().setAttribute("result", list);
        req.getSession().setAttribute("page", currentPage);
        req.getSession().setAttribute("pages_total", pagesTotal);
        req.getSession().setAttribute("command_name", ORDERS_COMMAND);
        req.getSession().setAttribute("operation", "orders");
        return "orders_list.jsp";
    }
}
