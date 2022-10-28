package com.myProject.service.command;

import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CompleteOrder implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        req.getSession().removeAttribute("order");
        req.getSession().removeAttribute("orderDetails");
        return "controller?command=command.back";
    }
}
