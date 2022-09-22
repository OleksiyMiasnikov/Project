package com.myProject.service.command;

import com.myProject.dao.entitie.User;
import com.myProject.employee.CommodityExpert;
import com.myProject.employee.Employee;
import com.myProject.exception.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Remains implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        new CommodityExpert(new User()).initWindow(req, resp);
    }
}
