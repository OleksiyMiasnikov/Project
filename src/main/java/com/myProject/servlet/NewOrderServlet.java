package com.myProject.servlet;

import com.myProject.dao.entitie.Order;
import com.myProject.dao.entitie.OrderDetails;
import com.myProject.employee.Employee;
import com.myProject.exception.DaoException;
import com.myProject.service.ProductManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/serveNewOrder")
public class NewOrderServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(NewOrderServlet.class);
    private Order currentOrder;
    private List<OrderDetails> currentOrderDetails;
    private long counter = 0L;
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
                currentOrder = new Order();
                currentOrderDetails = new ArrayList<>();
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
        showNewOrder(req, resp);
        logger.info("doGet finished");
    }

    private void addOrderDetails(HttpServletRequest req) {
        try {
            ProductManager productManager
                    = (ProductManager) getServletContext().getAttribute("ProductManager");
            if (currentOrder.getId() == 0) {
                currentOrder.setId(555L);
                logger.info("Created new order: " + currentOrder);
            }
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(currentOrder);
            orderDetails.setQuantity(Integer.parseInt(req.getParameter("newQuantity")));
            orderDetails.setPrice(Double.parseDouble(req.getParameter("newPrice")));
            orderDetails.setProduct(productManager.read(Long.parseLong(req.getParameter("newProductId"))));
            orderDetails.setId(counter++);
            currentOrderDetails.add(orderDetails);
            currentOrder.setTotalAmount(100);
            logger.info("Added order details: " + orderDetails);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    private void showNewOrder(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ProductManager productManager =
                    (ProductManager) req.getServletContext().getAttribute("ProductManager");
            if (currentOrder == null) {
                currentOrder = new Order();
                currentOrder.setUser(((Employee) req.getSession().getAttribute("Employee")).getUser());
                currentOrder.setDate(new Timestamp(new Date().getTime()));
                currentOrderDetails = new ArrayList<>();
            } else {
                req.setAttribute("orderDetails", currentOrderDetails);
            }
            req.setAttribute("order", currentOrder);
            req.setAttribute("products", productManager.findAllProducts());
            req.getRequestDispatcher("jsp/newOrder.jsp").forward(req, resp);
        } catch (DaoException | ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
