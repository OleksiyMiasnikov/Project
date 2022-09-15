package com.myProject.employee;

import com.myProject.dao.entitie.User;
import com.myProject.exception.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.myProject.dao.Constants.*;

public class CommodityExpert extends Employee {

    public CommodityExpert(User user) {
        super(user, COMMAND_NEW_GOODS, COMMAND_LIST_OF_GOODS, COMMAND_INCOME, COMMAND_REPORTS);
    }

    @Override
    public void initWindow(HttpServletRequest req, HttpServletResponse resp) throws DaoException {


        try {
            req.getRequestDispatcher("jsp/commodityExpertWindow.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

