package com.myProject.service.command;

import com.myProject.entitie.Order;
import com.myProject.entitie.OrderDetails;
import com.myProject.entitie.Product;
import com.myProject.entitie.Warehouse;
import com.myProject.service.employee.Employee;
import com.myProject.service.exception.DaoException;
import com.myProject.service.CashierManager;
import com.myProject.service.CommodityExpertManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.myProject.util.Constants.*;

public class NewOrder implements Command {
    private Order currentOrder;
    private CashierManager cashierManager;
    private CommodityExpertManager commodityExpertManager;
    private String direction;
    private String operation;
    String commandName;
    private static final Logger logger = (Logger) LogManager.getLogger(NewOrder.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("NewOrder started. Current order: " + currentOrder);
        cashierManager
                = (CashierManager) req.getSession().getServletContext().getAttribute("CashierManager");
        commodityExpertManager
                = (CommodityExpertManager) req.getSession().getServletContext().getAttribute("CommodityExpertManager");
        String button = req.getParameter("button");
        direction = req.getParameter("direction");
        if (direction == null) direction = "OUT";
        String exitCommandName;
        if ("IN".equals(direction)) {
            operation = "income";
            exitCommandName = "command.incomes";
            commandName = "command.new_income";
        } else {
            operation = "order";
            exitCommandName = "command.orders";
            commandName = "command.new_order";
        }
        if (button == null) button = "First invoke";
        switch (button) {
            case "Cancel" :
                cashierManager.deleteOrder(currentOrder.getId());
            case "Complete" :
                currentOrder = null;
                req.getSession().removeAttribute("order");
                req.getSession().removeAttribute("orderDetails");
                return "controller?command=" + exitCommandName;
            case "Log out" :
                currentOrder = null;
                logger.info("Log out pressed");
                return "controller?command=command.log_out";
            case "Save" :
                logger.info("Save pressed");
                addOrderDetails(req);
            default:
                showOrder(req, resp);
                return PATH + "new_order.jsp";
        }
    }

    private void addOrderDetails(HttpServletRequest req) throws DaoException {
        if (currentOrder.getId() == 0) {
            currentOrder = cashierManager.createOrder(currentOrder, direction);
        }

        double price = Double.parseDouble(req.getParameter("newPrice"));
        double quantity = Double.parseDouble(req.getParameter("newQuantity"));
        Product product = commodityExpertManager.read(Long.parseLong(req.getParameter("newProductId")));
        OrderDetails orderDetail = cashierManager
                .findOrderDetailByOrderAndProduct(currentOrder, product);
        if (orderDetail != null && Double.compare(price, orderDetail.getPrice()) == 0) {
            cashierManager.updateQuantityInOrderDetail(orderDetail.getId(), quantity);
        } else {
            orderDetail = new OrderDetails(0L, currentOrder, product, quantity, price);
            orderDetail = cashierManager.createOrderDetails(orderDetail, direction);
        }
        cashierManager.updateTotal(currentOrder.getId());
        currentOrder.setTotalAmount(cashierManager.read(currentOrder.getId()).getTotalAmount());
        logger.info("Added order details: " + orderDetail);
    }

    private void showOrder(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        String strPage = req.getParameter("page");
        List<Warehouse> warehouseList;
        int currentPage = 1;
        int pagesTotal = 1;
        if (currentOrder == null) {
            currentOrder = new Order();
            currentOrder.setUser(((Employee) req.getSession().getAttribute("employee")).getUser());
            currentOrder.setDate(new Timestamp(new Date().getTime()));
            req.getSession().removeAttribute("order");
            req.getSession().removeAttribute("orderDetails");
        } else {
            if (strPage != null &&
                    !"null".equals(strPage)) {
                currentPage = Integer.parseInt(strPage);
            }
            pagesTotal = (int)Math. ceil(cashierManager.findRowsTotalInOrderDetails(currentOrder.getId())/5d);
            List<OrderDetails> list = cashierManager.findAllOrderDetails((currentPage - 1) * 5, 5, currentOrder.getId());
            req.getSession().setAttribute("orderDetails", list);
        }
        if ("IN".equals(direction)) {
            List<Product> list = commodityExpertManager.findAllProducts(0, 1000);
            warehouseList = new ArrayList<>();
            for (Product element : list) {
                warehouseList.add(new Warehouse(0, 100000d, element));
            }
        } else {
            warehouseList = commodityExpertManager.findAll(0, 1000);
        }
        req.getSession().setAttribute("warehouse", warehouseList);
        req.getSession().setAttribute("page", currentPage);
        req.getSession().setAttribute("pages_total", pagesTotal);
        req.getSession().setAttribute("command_name", commandName);
        req.getSession().setAttribute("direction", direction);
        req.getSession().setAttribute("operation", operation);
        req.getSession().setAttribute("pcs", "шт");
        req.getSession().setAttribute("order", currentOrder);
        req.getSession().setAttribute("title", "command.new_" + operation);
    }
}
