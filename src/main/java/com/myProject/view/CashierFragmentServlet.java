package com.myProject.view;

import com.myProject.dao.entitie.Order;
import com.myProject.dao.entitie.Warehouse;
import com.myProject.service.employee.Employee;
import com.myProject.service.exception.DaoException;
import com.myProject.service.CashierManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.myProject.util.Constants.DELETE_ORDER_COMMAND;

@WebServlet("/CashierFragment")
public class CashierFragmentServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(CashierFragmentServlet.class);
    private double amountTotal;
    private double ordersTotal;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("service start");
        try (PrintWriter printWriter = resp.getWriter()){
            /*CashierManager cashierManager =
                    (CashierManager) getServletContext().getAttribute("CashierManager");
            List<Order> orderList = cashierManager.findAllOrders();*/
            List<Order> orderList = (List<Order>) req.getSession().getAttribute("result");
            CalculateOrdersTotal(orderList);
            String role = ((Employee)req.getSession()
                    .getAttribute("Employee"))
                    .getUser()
                    .getRole()
                    .getName();
            String strHide = "hidden = \"hidden\" ";
            if ("Senior cashier".equalsIgnoreCase(role)) {
                strHide = "";
            }
            printWriter.write("List of orders. [CashierFragmentServlet: /CashierFragment]");
            printWriter.write("<br>");
            printWriter.write("<span style=\"width: 250px;\">Quantity of orders: " + ordersTotal + "</span>");
            printWriter.write("<br>");
            printWriter.write("<span style=\"width: 250px;\">Total orders amount : " + amountTotal + "</span>");
            printWriter.write("<br>");

            printWriter.write("<div class=\"table_header\">");
            printWriter.write("<button type=\"submit\" " +
                                "name=\"command\" " +
                                "value=\"" +
                    DELETE_ORDER_COMMAND +
                                "\"" +
                                "class=\"table_header\" " +
                                "style=\"width: 50px; color: black;\"" +
                                strHide +
                                ">" +
                                "<i class=\"fa-solid fa-trash-can\"></i>" +
                                "</button>");
            printWriter.write("<span class=\"table_header\" style=\"width: 50px;\">Id</span>");
            printWriter.write("<span class=\"table_header\" style=\"width: 200px;\">Time</span>");
            printWriter.write("<span class=\"table_header\" style=\"width: 100px;\">Amount</span>");
            printWriter.write("<span class=\"table_header\" style=\"width: 100px;\">Employee</span>");
            printWriter.write("</div>");
            printWriter.write("<br><hr>");

            for (Order element : orderList) {
                printWriter.write("<input type=\"checkbox\" style=\"width: 50px;text-align:center;\" " +
                        "name=\"orders\" " +
                        "id=\"myCheck\" " +
                        strHide +
                        "value=\"" +
                        element.getId() +
                        "\">");
                printWriter.write("<span class=\"item\" style=\"width: 50px;text-align: center;\">");
                printWriter.write("<a href=\"serveOrder?id="
                        + element.getId()
                        + "\">");
                printWriter.write(String.valueOf(element.getId()));
                printWriter.write("</a>");
                printWriter.write("</span>");
                printWriter.write("<span class=\"item\" style=\"width: 200px;\">");
                printWriter.write(String.valueOf(element.getDate()));
                printWriter.write("</span>");
                printWriter.write("<span class=\"item\" style=\"width: 100px;\">");
                printWriter.write(String.valueOf(element.getTotalAmount()));
                printWriter.write("</span>");
                printWriter.write("<span class=\"item\" style=\"width: 100px;\">");
                printWriter.write(element.getUser().getLogin());
                printWriter.write("</span>");
                printWriter.write("<br><hr>");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("service finish");
    }

    private void CalculateOrdersTotal(List<Order> list) {
        if (list == null) return;
        ordersTotal = 0;
        amountTotal = 0;
        for (Order element : list) {
            ordersTotal ++;
            amountTotal += element.getTotalAmount();
        }
    }
}
