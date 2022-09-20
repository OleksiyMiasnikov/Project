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

@WebServlet("/serveNewOrder")
public class NewOrderServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(NewOrderServlet.class);
    private Order currentOrder;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("doPost started");
        OrderManager orderManager
                = (OrderManager) getServletContext().getAttribute("OrderManager");
        String newId = req.getParameter("newId");

        if ("0".equals(newId)) {
            logger.info("first record");
            currentOrder.setTotalAmount(Double.parseDouble(req.getParameter("newTotal")));
            try {
                currentOrder = orderManager.create(currentOrder);
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
            logger.info(currentOrder);
        }
        OrderDetails orderDetails = new OrderDetails();
        GoodsManager goodsManager
                = (GoodsManager) getServletContext().getAttribute("GoodsManager");
        long goodsId = Long.parseLong(req.getParameter("newGoodsId"));
        int quantity = Integer.parseInt(req.getParameter("newQuantity"));
        double price = Double.parseDouble(req.getParameter("newPrice"));
        orderDetails.setOrder(currentOrder);
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
        resp.sendRedirect("serveNewOrder?id=" + currentOrder.getId());
        logger.info("doPost finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("doGet started. Current order: " + currentOrder);
        try {
            String strId = req.getParameter("id");
            if (currentOrder == null) {
                currentOrder = new Order();
                currentOrder.setUser(((Employee) req.getSession().getAttribute("Employee")).getUser());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                currentOrder.setDate(new Timestamp(new Date().getTime()));
            } else {
                OrderManager orderManager =
                        (OrderManager) req.getServletContext().getAttribute("OrderManager");
                OrderDetailsManager orderDetailsManager =
                        (OrderDetailsManager) req.getServletContext().getAttribute("OrderDetailsManager");
                try {
                    List<OrderDetails> orderDetailsList = orderDetailsManager.detailsByOrderId(currentOrder.getId());
                    logger.info(orderDetailsList);
                    req.setAttribute("orderDetails", orderDetailsList);

                } catch ( DaoException e) {
                    throw new RuntimeException(e);
                }
            }
            req.setAttribute("order", currentOrder);
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
        logger.info("doGet finished");
    }
}
