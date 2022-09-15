package com.myProject.view;

import com.myProject.employee.Employee;
import com.myProject.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/main")
public class MainWindow extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(MainWindow.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("Employee");
        session.setAttribute("menuItems", employee.getMenuItems());
        String employeeRole = employee.getUser().getRole().getName();
        try {
            employee.initWindow(req, resp);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("main doPost");
        String window = (String) req.getAttribute("Window");
        logger.info(window);
        req.getRequestDispatcher(window).forward(req, resp);

        //req.getRequestDispatcher("Jsp/adminWindow.jsp").forward(req, resp);

    }

}
