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
import java.util.List;

@WebServlet("/SeniorCashierFragment")
public class SeniorCashierFragmentServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(SeniorCashierFragmentServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("service start");
        try (PrintWriter printWriter = resp.getWriter()){
            CashierManager cashierManager =
                    (CashierManager) getServletContext()
                            .getAttribute("CashierManager");
            List<Order> orderList = cashierManager.findAllOrders();

            printWriter.write("<input type=\"checkbox\" id=\"myCheck\" hidden=\"hidden\">");
            for (Order element : orderList) {
                printWriter.write("<input type=\"checkbox\" " +
                        "name=\"orders\" " +
                        "id=\"myCheck\" " +
                        "value=\"" +
                        element.getId() +
                        "\">");
                printWriter.write(" | ");
                printWriter.write("<a href=\"serveOrder?id="
                        + element.getId()
                        + "\">");
                printWriter.write(String.valueOf(element.getId()));
                printWriter.write("</a>");
                printWriter.write(" | ");
                printWriter.write(String.valueOf(element.getDate()));
                printWriter.write(" | ");
                printWriter.write(String.valueOf(element.getTotalAmount()));
                printWriter.write(" | ");
                printWriter.write(element.getUser().getLogin());
                printWriter.write("<br>");
            }
        } catch (IOException | DaoException e) {
            throw new RuntimeException(e);
        }
        logger.info("service finish");
    }
}
