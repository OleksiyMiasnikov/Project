package com.myProject.service.command;

import com.myProject.dao.entitie.Order;
import com.myProject.service.CashierManager;
import com.myProject.service.exception.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.myProject.util.Constants.MAIN_PAGE;

public class Incomes implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        CashierManager cashierManager =
                (CashierManager) req.getSession().getServletContext().getAttribute("CashierManager");
        req.getSession().setAttribute("result", cashierManager.findAllIncomes());
        req.getSession().setAttribute("Fragment", "/IncomesFragment");
        return MAIN_PAGE;
    }
}
