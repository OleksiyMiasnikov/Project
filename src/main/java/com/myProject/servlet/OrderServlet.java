package com.myProject.servlet;

import com.myProject.dao.entitie.Goods;
import com.myProject.dao.entitie.Order;
import com.myProject.dao.entitie.OrderDetails;
import com.myProject.employee.Employee;
import com.myProject.exception.DaoException;
import com.myProject.service.GoodsManager;
import com.myProject.service.OrderDetailsManager;
import com.myProject.service.OrderManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@WebServlet("/serveOrder")
public class OrderServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(OrderServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doPost started");
        try {
            ((Employee) req.getSession().getAttribute("Employee")).initWindow(req, resp);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        logger.info("doPost finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("doGet started");
        String strId = req.getParameter("id");
        if (strId == null) {
            newOrder(req, resp);
        } else {
            showOrder(req, resp, strId);
        }

        logger.info("doGet finished");
    }

    private void newOrder(HttpServletRequest req, HttpServletResponse resp) {

        try {
            Order order = new Order();
            order.setUser(((Employee) req.getSession().getAttribute("Employee")).getUser());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            order.setDate(new Timestamp(new Date().getTime()));
            logger.info(order.getDate());
            req.setAttribute("order", order);
            GoodsManager goodsManager =
                    (GoodsManager) req.getSession()
                            .getServletContext()
                            .getAttribute("GoodsManager");
            List<Goods> goodsList = goodsManager.findAllGoods();
            logger.info(goodsList);
            req.setAttribute("goods", goodsList);
            req.getRequestDispatcher("jsp/newOrder.jsp").forward(req, resp);
        } catch (ServletException | IOException | DaoException e) {
            throw new RuntimeException(e);
        }
    }

    private void showOrder(HttpServletRequest req, HttpServletResponse resp, String strId) {
        long id = Long.parseLong(strId);
        OrderManager orderManager = (OrderManager) req.getServletContext().getAttribute("OrderManager");
        OrderDetailsManager orderDetailsManager = (OrderDetailsManager) req.getServletContext().getAttribute("OrderDetailsManager");
        try {
            Order order = orderManager.read(id);
            logger.info(order);
            List<OrderDetails> orderDetailsList = orderDetailsManager.detailsByOrderId(id);
            logger.info(orderDetailsList);
            req.setAttribute("orderDetails", orderDetailsList);
            req.setAttribute("order", order);
            req.getRequestDispatcher("jsp/order.jsp").forward(req, resp);
        } catch (ServletException | IOException | DaoException e) {
            throw new RuntimeException(e);
        }
    }
}
