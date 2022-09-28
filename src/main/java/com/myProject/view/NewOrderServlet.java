package com.myProject.view;

import com.myProject.dao.entitie.Order;
import com.myProject.dao.entitie.OrderDetails;
import com.myProject.service.employee.Employee;
import com.myProject.service.exception.DaoException;
import com.myProject.service.CashierManager;
import com.myProject.service.CommodityExpertManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import static com.myProject.util.Constants.*;

@WebServlet("/serveNewOrder")
public class NewOrderServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(NewOrderServlet.class);
    private Order currentOrder;
    CashierManager cashierManager;
    CommodityExpertManager commodityExpertManager;

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
                currentOrder = null;
                logger.info("Complete pressed");
                resp.sendRedirect("controller?command=" + ORDERS_COMMAND);
                break;
            case "Cancel" :
                try {
                    cashierManager.deleteOrder(currentOrder.getId());
                } catch (DaoException e) {
                    throw new RuntimeException(e);
                }
                currentOrder = null;
                logger.info("Cancel pressed");
                resp.sendRedirect("controller?command=" + ORDERS_COMMAND);
                break;
            case "Log out" :
                currentOrder = null;
                logger.info("Log out pressed");
                resp.sendRedirect("controller?command=" + LOGOUT_COMMAND);
                break;
        }
        logger.info("doPost finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("doGet started. Current order: " + currentOrder);
        cashierManager
                = (CashierManager) getServletContext().getAttribute("CashierManager");
        commodityExpertManager
                = (CommodityExpertManager) getServletContext().getAttribute("CommodityExpertManager");
        showOrder(req, resp);
        logger.info("doGet finished");
    }

    private void addOrderDetails(HttpServletRequest req) {
        try {
            if (currentOrder.getId() == 0) {
                currentOrder = cashierManager.createOrder(currentOrder);
                logger.info("Created new order: " + currentOrder);
            }
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(currentOrder);
            orderDetails.setQuantity(Double.parseDouble(req.getParameter("newQuantity")));
            orderDetails.setPrice(Double.parseDouble(req.getParameter("newPrice")));
            orderDetails.setProduct(commodityExpertManager.read(Long.parseLong(req.getParameter("newProductId"))));
            orderDetails = cashierManager.createOrderDetails(orderDetails);
            cashierManager.updateTotal(currentOrder.getId());
            currentOrder.setTotalAmount(cashierManager.read(currentOrder.getId()).getTotalAmount());
            logger.info("Added order details: " + orderDetails);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showOrder(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (currentOrder == null) {
                currentOrder = new Order();
                currentOrder.setUser(((Employee) req.getSession().getAttribute("employee")).getUser());
                currentOrder.setDate(new Timestamp(new Date().getTime()));
            } else {
                req.setAttribute("orderDetails", cashierManager.detailsByOrderId(currentOrder.getId()));
            }
            req.setAttribute("order", currentOrder);
            req.setAttribute("warehouse", commodityExpertManager.findAll(0, 1000));
            logger.info("products in warehouse: " + commodityExpertManager.findAll(0, 1000));
            req.getRequestDispatcher("new_order.jsp").forward(req, resp);
        } catch (DaoException | ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
