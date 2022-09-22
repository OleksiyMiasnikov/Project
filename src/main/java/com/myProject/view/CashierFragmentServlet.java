package com.myProject.view;

import com.myProject.dao.entitie.Order;
import com.myProject.exception.DaoException;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/CashierFragment")
public class CashierFragmentServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(CashierFragmentServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("service start");
        try (PrintWriter printWriter = resp.getWriter()){
            CashierManager cashierManager =
                    (CashierManager) getServletContext().getAttribute("CashierManager");
            List<Order> orderList = cashierManager.findAllOrders();
            printWriter.write("<div class=\"table_header\">");
            printWriter.write("<span style=\"width: 50px;\">Id</span>");
            printWriter.write("<span style=\"width: 200px;\">Time</span>");
            printWriter.write("<span style=\"width: 100px;\">Amount</span>");
            printWriter.write("<span style=\"width: 100px;\">Employee</span>");
            printWriter.write("</div>");
            printWriter.write("<br><hr>");

            for (Order element : orderList) {
                printWriter.write("<span style=\"float: left; width: 50px;text-align: center;\">");
                printWriter.write("<a href=\"serveOrder?id="
                        + element.getId()
                        + "\">");
                printWriter.write(String.valueOf(element.getId()));
                printWriter.write("</a>");
                printWriter.write("</span>");
                printWriter.write("<span style=\"float: left; width: 200px;\">");
                printWriter.write(String.valueOf(element.getDate()));
                printWriter.write("</span>");
                printWriter.write("<span style=\"float: left; width: 100px;\">");
                printWriter.write(String.valueOf(element.getTotalAmount()));
                printWriter.write("</span>");
                printWriter.write("<span style=\"float: left; width: 100px;\">");
                printWriter.write(element.getUser().getLogin());
                printWriter.write("</span>");
                printWriter.write("<br><hr>");
            }
        } catch (IOException | DaoException e) {
            throw new RuntimeException(e);
        }
        logger.info("service finish");
    }
}
