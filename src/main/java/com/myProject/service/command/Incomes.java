package com.myProject.service.command;

import com.myProject.dao.entitie.Order;
import com.myProject.service.CashierManager;
import com.myProject.service.exception.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.myProject.util.Constants.*;

public class Incomes implements Command {
    private double amountTotal;
    private double ordersTotal;
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        CashierManager manager =
                (CashierManager) req.getSession().getServletContext().getAttribute("CashierManager");

        String strPage = req.getParameter("page");
        int currentPage = 1;
        if (strPage != null && !"".equals(strPage)) currentPage = Integer.parseInt(strPage);
        int pagesTotal = (int)Math. ceil(manager.findIncomeRowsTotal()/10d);
        List<Order> list = manager.findAll((currentPage - 1) * 10, 10, "IN");
        CalculateOrdersTotal(list);
        req.getSession().setAttribute("result", list);
        req.getSession().setAttribute("amountTotal", amountTotal);
        req.getSession().setAttribute("ordersTotal", ordersTotal);
        req.getSession().setAttribute("result", list);
        req.getSession().setAttribute("page", currentPage);
        req.getSession().setAttribute("pages_total", pagesTotal);
        req.getSession().setAttribute("command_name", INCOMES_COMMAND);
        return "incomes_list.jsp";
    }

    private void CalculateOrdersTotal(List<Order> list) {
        if (list == null) return;
        ordersTotal = 0;
        amountTotal = 0;
        for (Order element : list) {
            ordersTotal ++;
            amountTotal += element.getTotalAmount();
        }
    }
}
