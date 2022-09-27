package com.myProject.service.employee;

import com.myProject.dao.entitie.User;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.myProject.util.Constants.*;

public class SeniorCashier extends Employee{
    private static final Logger logger = (Logger) LogManager.getLogger(SeniorCashier.class);
    public SeniorCashier(User user) {
        super(user, ORDERS_COMMAND, NEW_ORDER_COMMAND, ORDERS_COMMAND, REMAINS_COMMAND, REPORTS_COMMAND);
    }
}
