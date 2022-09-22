package com.myProject.employee;

import com.myProject.dao.entitie.User;
import com.myProject.exception.DaoException;
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
        super(user, COMMAND_NEW_ORDER, COMMAND_DELETE_ORDER, COMMAND_DELETE_ORDER_PRODUCT, COMMAND_REPORTS);
    }

    @Override
    public void initWindow(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        logger.info("initWindow");
        req.setAttribute("Fragment", "/SeniorCashierFragment");
        try {
            req.getRequestDispatcher("jsp/mainWindow.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
