package com.myProject.view;

import com.myProject.dao.entitie.Product;
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

@WebServlet("/ProductListFragment")
public class ProductListServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(ProductListServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("service start");
        List<Product> productList = (List<Product>) req.getAttribute("result");
        try (PrintWriter printWriter = resp.getWriter()){
            printWriter.write("<div class=\"table_header\">");
            printWriter.write("<span style=\"width: 50px;\">Id</span>");
            printWriter.write("<span style=\"width: 200px;\">Product name</span>");
            printWriter.write("<span style=\"width: 50px;\">Unit</span>");
            printWriter.write("<span style=\"width: 100px;text-align: right;\">Price</span>");
            printWriter.write("</div>");
            printWriter.write("<br><hr>");

            for (Product element : productList) {
                printWriter.write("<span style=\"float: left; width: 50px;text-align: center;\">");
                printWriter.write(String.valueOf(element.getId()));
                printWriter.write("</span>");
                printWriter.write("<span style=\"float: left; width: 200px;\">");
                printWriter.write(element.getName());
                printWriter.write("</span>");
                printWriter.write("<span style=\"float: left; width: 50px;text-align: center;\">");
                printWriter.write(element.getUnit().labelUa);
                printWriter.write("</span>");
                printWriter.write("<span style=\"float: left; width: 100px;text-align: right;\">");
                printWriter.write(String.valueOf(element.getPrice()));
                printWriter.write("</span>");
                printWriter.write("<br><hr>");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("service finish");
    }
}
