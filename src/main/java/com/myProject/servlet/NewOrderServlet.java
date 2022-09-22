package com.myProject.servlet;

import com.myProject.dao.entitie.Order;
import com.myProject.dao.entitie.OrderDetails;
import com.myProject.employee.Employee;
import com.myProject.exception.DaoException;
import com.myProject.service.CashierManager;
import com.myProject.service.ProductManager;
import com.myProject.service.WarehouseManager;
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

@WebServlet("/serveNewOrder")
public class NewOrderServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(NewOrderServlet.class);
    private Order currentOrder;
    ProductManager productManager;
    CashierManager cashierManager;
    WarehouseManager warehouseManager;

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
                try {
                    ((Employee)req.getSession().getAttribute("Employee")).initWindow(req, resp);
                } catch (DaoException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "Cancel" :
// --------------------------------deleteOrder order
                try {
                    cashierManager.deleteOrder(currentOrder.getId());
                } catch (DaoException e) {
                    throw new RuntimeException(e);
                }
                currentOrder = null;
                logger.info("Cancel pressed");
                try {
                    ((Employee)req.getSession().getAttribute("Employee")).initWindow(req, resp);
                } catch (DaoException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
        logger.info("doPost finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("doGet started. Current order: " + currentOrder);
        productManager = (ProductManager) getServletContext().getAttribute("ProductManager");
        cashierManager = (CashierManager) getServletContext().getAttribute("CashierManager");
        warehouseManager =  (WarehouseManager) getServletContext().getAttribute("WarehouseManager");
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
            orderDetails.setProduct(productManager.read(Long.parseLong(req.getParameter("newProductId"))));
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
                currentOrder.setUser(((Employee) req.getSession().getAttribute("Employee")).getUser());
                currentOrder.setDate(new Timestamp(new Date().getTime()));
            } else {
                req.setAttribute("orderDetails", cashierManager.detailsByOrderId(currentOrder.getId()));
            }
            req.setAttribute("order", currentOrder);
            req.setAttribute("warehouse", warehouseManager.findAll());
            logger.info("products in warehouse: " + warehouseManager.findAll());
            req.getRequestDispatcher("jsp/newOrder.jsp").forward(req, resp);
        } catch (DaoException | ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
