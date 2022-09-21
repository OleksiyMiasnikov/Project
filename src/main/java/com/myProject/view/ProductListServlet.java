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
            for (Product element : productList) {
                printWriter.write(String.valueOf(element.getId()));
                printWriter.write("--");
                printWriter.write(element.getName());
                printWriter.write("--");
                printWriter.write(element.getUnit().labelUa);
                printWriter.write("--");
                printWriter.write(String.valueOf(element.getPrice()));
                printWriter.write("<br>");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("service finish");
    }
}
