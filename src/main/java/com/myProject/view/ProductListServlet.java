package com.myProject.view;

import com.myProject.dao.entitie.Product;
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

import static com.myProject.util.Constants.*;

@WebServlet("/ProductListFragment")
public class ProductListServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(ProductListServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("service start");
        List<Product> productList = (List<Product>) req.getSession().getAttribute("result");
        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write("<span style=\"font-size: 8px;\">" +
                                "[ProductListServlet: /ProductListFragment]" +
                                "</span>");
            printWriter.write("<p style=\"text-align: center; font-size: 22px;font-weight: bold\">" +
                                "*** List of products ***" +
                                "</p>");
            printWriter.write("<hr>");
            printWriter.write("<div class=\"table_header\">");
            printWriter.write("<span class=\"table_header\" style=\"width: 50px;\">Id</span>");
            printWriter.write("<span class=\"table_header\" style=\"width: 250px;\">Product name</span>");
            printWriter.write("<span class=\"table_header\" style=\"width: 50px;\">Unit</span>");
            printWriter.write("<span class=\"table_header\" style=\"width: 150px;text-align: right;\">Price</span>");
            printWriter.write("</div>");
            printWriter.write("<br><hr>");
            printWriter.write("<div class=\"data_list\">");
            for (Product element : productList) {
                printWriter.write("<span class=\"item\" style=\"width: 50px;text-align: center;\">");
                printWriter.write(String.valueOf(element.getId()));
                printWriter.write("</span>");
                printWriter.write("<span class=\"item\" style=\"width: 250px;text-align: center;\">");
                printWriter.write("<a " +
                        "href=\"controller?command=" +
                        NEW_PRODUCT_COMMAND +
                        "&selectedProduct=" +
                        element.getId() +
                        "\">");
                printWriter.write(element.getName());
                printWriter.write("</a>");
                printWriter.write("</span>");
                printWriter.write("<span class=\"item\" style=\"width: 50px;text-align: center;\">");
                printWriter.write(element.getUnit().labelUa);
                printWriter.write("</span>");
                printWriter.write("<span class=\"item\" style=\"width: 150px;text-align: right;\">");
                printWriter.write(String.valueOf(element.getPrice()));
                printWriter.write("</span>");
                printWriter.write("<br><hr>");
            }
            printWriter.write("</div>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("service finish");
    }
}
