package com.myProject.servlet;

import com.myProject.dao.OrderDao;
import com.myProject.dao.entitie.Goods;
import com.myProject.dao.entitie.OrderDetails;
import com.myProject.employee.Employee;
import com.myProject.exception.DaoException;
import com.myProject.service.GoodsManager;
import com.myProject.service.OrderDetailsManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/serveOrder")
public class OrderServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(OrderServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ((Employee) req.getSession().getAttribute("Employee")).initWindow(req, resp);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("doGet started");
        String strId = req.getParameter("id");
        long id = Long.parseLong(strId);
        OrderDetailsManager orderDetailsManager = (OrderDetailsManager) req.getServletContext().getAttribute("OrderDetailsManager");
        try {
            List<OrderDetails> orderDetailsList = orderDetailsManager.detailsByOrderId(id);
            logger.info(orderDetailsList);
            req.setAttribute("orderDetails", orderDetailsList);
            req.getRequestDispatcher("jsp/orderDetails.jsp").forward(req, resp);
        } catch (ServletException | IOException | DaoException e) {
            throw new RuntimeException(e);
        }
        logger.info("doGet finished");
    }
}
