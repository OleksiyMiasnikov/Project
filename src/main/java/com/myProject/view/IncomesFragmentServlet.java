package com.myProject.view;

import com.myProject.dao.entitie.Order;
import com.myProject.service.exception.DaoException;
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

@WebServlet("/IncomesFragment")
public class IncomesFragmentServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(IncomesFragmentServlet.class);

    private double amountTotal;
    private double ordersTotal;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("service start");
        try (PrintWriter printWriter = resp.getWriter()){
            List<Order> incomeList = (List<Order>) req.getSession().getAttribute("result");
            CalculateOrdersTotal(incomeList);
            printWriter.write("<span style=\"font-size: 8px;\">" +
                    "[IncomesFragmentServlet: /IncomesFragment]" +
                    "</span>");
            printWriter.write("<p style=\"text-align: center; font-size: 22px;font-weight: bold\">" +
                    "*** List of incomes ***" +
                    "</p>");
            printWriter.write("<hr>");
            printWriter.write("<span style=\"width: 250px;\">Quantity of orders: " + ordersTotal + "</span>");
            printWriter.write("<br>");
            printWriter.write("<span style=\"width: 250px;\">Total orders amount : " + amountTotal + "</span>");
            printWriter.write("<br><hr>");
            printWriter.write("<div class=\"table_header\">");
            printWriter.write("<span class=\"table_header\" style=\"width: 50px;\">Id</span>");
            printWriter.write("<span class=\"table_header\" style=\"width: 200px;\">Time</span>");
            printWriter.write("<span class=\"table_header\" style=\"width: 120px;\">Amount</span>");
            printWriter.write("<span class=\"table_header\" style=\"width: 130px;\">Employee</span>");
            printWriter.write("</div>");
            printWriter.write("<br><hr>");
            printWriter.write("<div class=\"data_list\">");
            for (Order element : incomeList) {
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
                printWriter.write("<span class=\"item\" style=\"width: 120px;\">");
                printWriter.write(String.valueOf(element.getTotalAmount()));
                printWriter.write("</span>");
                printWriter.write("<span class=\"item\" style=\"width: 130px;\">");
                printWriter.write(element.getUser().getLogin());
                printWriter.write("</span>");
                printWriter.write("<br><hr>");
            }
            printWriter.write("</div>");
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
