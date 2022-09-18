package com.myProject.view;

import com.myProject.dao.entitie.Order;
import com.myProject.dao.entitie.Warehouse;
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

@WebServlet("/CashierFragment")
public class CashierFragmentServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(CashierFragmentServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("service start");
        List<Order> orderList;
        orderList = (List<Order>) req.getAttribute("result");
        try (PrintWriter printWriter = resp.getWriter()){
            for (Order element : orderList) {
                printWriter.write("<a href=\"serveOrder?id="
                        + element.getId()
                        + "\">");
                printWriter.write(String.valueOf(element.getId()));
                printWriter.write("</a>");
                printWriter.write("--");
                printWriter.write(String.valueOf(element.getDate()));
                printWriter.write("--");
                printWriter.write(String.valueOf(element.getAmount()));
                printWriter.write("--");
                printWriter.write(element.getUser().getLogin());
                printWriter.write("<br>");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("service finish");
    }
}
