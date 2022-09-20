package com.myProject.servlet;

import com.myProject.dao.entitie.Goods;
import com.myProject.dao.entitie.Order;
import com.myProject.dao.entitie.OrderDetails;
import com.myProject.employee.Employee;
import com.myProject.exception.DaoException;
import com.myProject.service.GoodsManager;
import com.myProject.service.OrderDetailsManager;
import com.myProject.service.OrderManager;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@WebServlet("/serveOrder")
public class OrderServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(OrderServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doPost started");
        OrderManager orderManager
                = (OrderManager) getServletContext().getAttribute("OrderManager");
        Order newOrder = null;
        String newId = req.getParameter("newId");

        if ("0".equals(newId)) {
            // add new order
            // + add new orderDetails
            logger.info("first record");
            SimpleDateFormat formatter =
                    new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
                            new Locale("uk","UA"));
            Date newDate = null;
            try {
                newDate = formatter.parse(req.getParameter("newDate"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            newOrder = new Order();
            newOrder.setId(Long.parseLong(newId));
            newOrder.setDate(newDate);
            newOrder.setTotalAmount(Double.parseDouble(req.getParameter("newTotal")));
            UserManager userManager
                    = (UserManager) getServletContext().getAttribute("UserManager");
            try {
                newOrder.setUser(userManager.findUser(req.getParameter("newUser")));
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
            try {
                newOrder = orderManager.create(newOrder);
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
            logger.info(newOrder);
        } else {
            try {
                newOrder = orderManager.read(Long.parseLong(newId));
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        }

        OrderDetails orderDetails = new OrderDetails();
        GoodsManager goodsManager
                = (GoodsManager) getServletContext().getAttribute("GoodsManager");
        long goodsId = Long.parseLong(req.getParameter("newGoodsId"));
        int quantity = Integer.parseInt(req.getParameter("newQuantity"));
        double price = Double.parseDouble(req.getParameter("newPrice"));
        orderDetails.setOrder(newOrder);
        try {
            orderDetails.setGoods(goodsManager.read(goodsId));
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        orderDetails.setQuantity(quantity);
        orderDetails.setPrice(price);

        OrderDetailsManager orderDetailsManager
                = (OrderDetailsManager) getServletContext().getAttribute("OrderDetailsManager");
        try {
            orderDetails = orderDetailsManager.create(orderDetails);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        logger.info(orderDetails);


        //resp.sendRedirect("serveOrder?id=" + order.getId());
        /*try {
            ((Employee) req.getSession().getAttribute("Employee")).initWindow(req, resp);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }*/
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
