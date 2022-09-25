package com.myProject.service.command;

import com.myProject.dao.entitie.Order;
import com.myProject.employee.Employee;
import com.myProject.exception.DaoException;
import com.myProject.service.CashierManager;
import com.myProject.service.CommodityExpertManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class NewOrder implements Command {
    private Order currentOrder;
    CashierManager cashierManager;
    CommodityExpertManager commodityExpertManager;
    private static final Logger logger = (Logger) LogManager.getLogger(NewOrder.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {

        return "serveNewOrder";
    }

}
