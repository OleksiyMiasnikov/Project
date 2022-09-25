package com.myProject.service.command;

import com.myProject.dao.entitie.User;
import com.myProject.employee.Cashier;
import com.myProject.exception.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Orders implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        new Cashier(new User()).initWindow(req, resp);
        return null;
    }
}
