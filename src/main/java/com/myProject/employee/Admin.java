package com.myProject.employee;

import com.myProject.dao.entitie.User;
import com.myProject.exception.DbException;
import com.myProject.service.UserManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.myProject.dao.Constants.*;

public class Admin extends Employee{
    public Admin(User user) {
        super(user, COMMAND_ADD_USER, COMMAND_UPDATE_USER, COMMAND_DELETE_USER);
    }

    @Override
    public void initWindow(HttpServletRequest req, HttpServletResponse resp) throws DbException {
        UserManager userManager = (UserManager) req.getSession().getServletContext().getAttribute("UserManager");
        List<User> userList = null;
        userList = userManager.findAllUsers();
        req.setAttribute("result", userList);
        try {
            req.getRequestDispatcher("jsp/adminWindow.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
