package com.myProject.employee;

import com.myProject.dao.entitie.User;
import com.myProject.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.myProject.dao.Constants.*;

public class SeniorCashier extends Employee{
    public SeniorCashier(User user) {
        super(user, COMMAND_NEW_ORDER, COMMAND_REPORTS);
    }

    @Override
    public void initWindow(HttpServletRequest req, HttpServletResponse resp) throws DaoException {

    }
}
