package com.myProject.service.command;

import com.myProject.entitie.Order;
import com.myProject.service.CashierManager;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelOrder implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        CashierManager cashierManager
                = (CashierManager) req.getServletContext().getAttribute("CashierManager");
        Order currentOrder = (Order) req.getSession().getAttribute("order");
        cashierManager.deleteOrder(currentOrder.getId(), "OUT");
        return "controller?command=command.complete_order";
    }
}
