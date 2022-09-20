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
        switch (req.getParameter("button")) {
            case "Save" :
                logger.info("Save pressed");
                addOrderDetails(req);
                resp.sendRedirect("serveNewOrder");
                break;
            case "Complete" :
                logger.info("Complete pressed");
                break;
            case "Cancel" :
                logger.info("Cancel pressed");
                break;
        }
        logger.info("doPost finished");
    }

    private void addOrderDetails(HttpServletRequest req) {
        try {
            OrderManager orderManager
                    = (OrderManager) getServletContext().getAttribute("OrderManager");
            OrderDetailsManager orderDetailsManager
                    = (OrderDetailsManager) getServletContext().getAttribute("OrderDetailsManager");
            GoodsManager goodsManager
                    = (GoodsManager) getServletContext().getAttribute("GoodsManager");
            if (currentOrder.getId() == 0) {
                currentOrder = orderManager.create(currentOrder);
                logger.info("Created new order: " + currentOrder);
            }
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(currentOrder);
            orderDetails.setQuantity(Integer.parseInt(req.getParameter("newQuantity")));
            orderDetails.setPrice(Double.parseDouble(req.getParameter("newPrice")));
            orderDetails.setGoods(goodsManager.read(Long.parseLong(req.getParameter("newGoodsId"))));
            orderDetails = orderDetailsManager.create(orderDetails);
            orderManager.updateTotal(currentOrder.getId());
            currentOrder.setTotalAmount(orderManager.read(currentOrder.getId()).getTotalAmount());
            logger.info("Added order details: " + orderDetails);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("doGet started. Current order: " + currentOrder);
        try {
            GoodsManager goodsManager =
                    (GoodsManager) req.getSession().getServletContext().getAttribute("GoodsManager");
            if (currentOrder == null) {
                currentOrder = new Order();
                currentOrder.setUser(((Employee) req.getSession().getAttribute("Employee")).getUser());
                currentOrder.setDate(new Timestamp(new Date().getTime()));
            } else {
                OrderDetailsManager orderDetailsManager =
                        (OrderDetailsManager) req.getServletContext().getAttribute("OrderDetailsManager");
                req.setAttribute("orderDetails", orderDetailsManager.detailsByOrderId(currentOrder.getId()));
            }
            req.setAttribute("order", currentOrder);
            req.setAttribute("goods", goodsManager.findAllGoods());
            req.getRequestDispatcher("jsp/newOrder.jsp").forward(req, resp);
        } catch (ServletException | IOException | DaoException e) {
            throw new RuntimeException(e);
        }
        logger.info("doGet finished");
    }
}
