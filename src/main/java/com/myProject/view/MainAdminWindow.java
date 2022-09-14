package com.myProject.view;

import com.myProject.dao.entitie.User;
import com.myProject.employee.Employee;
import com.myProject.exception.DbException;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;



@WebServlet("/main")
public class MainAdminWindow extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(MainAdminWindow.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("Employee");

        session.setAttribute("menuItems", employee.getMenuItems());
        session.setAttribute("menuTitle", employee.getUser().getRole().getName());
        session.setAttribute("result", "User: "
                                            + employee.getUser().getLogin()
                                            + "<br>Has role: "
                                            + employee.getUser().getRole().getName());
        UserManager userManager = (UserManager) req.getSession().getServletContext().getAttribute("UserManager");
        List<User> userList = null;
        try {
            userList = userManager.findAllUsers();
        } catch (DbException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("result", userList);
        req.getRequestDispatcher("Jsp/mainWindow.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("main doPost");
        req.getRequestDispatcher("Jsp/mainWindow.jsp").forward(req, resp);

    }

}
