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

@WebServlet("/NewProductFragment")
public class NewProductFragmentServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(NewProductFragmentServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("service start");
        List<Product> productList = (List<Product>) req.getSession().getAttribute("result");
        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write("<span style=\"font-size: 8px;\">" +
                                "[NewProductFragmentServlet: /NewProductFragment" +
                                "</span>");
            printWriter.write("<p style=\"text-align: center; font-size: 22px;font-weight: bold\">" +
                                "*** New product ***" +
                                "</p>");
            printWriter.write("<hr>");
            printWriter.write("<div class=\"table_header\">");
            printWriter.write("<span class=\"header_key\" style=\"width: 150px;text-align: left;\">" +
                                "Product name" +
                                "</span>");
            printWriter.write("<input class=\"header_value\" name=\"newName\" value=\"${product.name}\">");
            printWriter.write("<br>");
            printWriter.write("<span class=\"header_key\" style=\"width: 150px;text-align: left;\">" +
                                "Unit" +
                                "</span>");
            printWriter.write("<input class=\"header_value\" name=\"newUnit\" value=\"${product.unit}\">");
            printWriter.write("<br>");
            printWriter.write("<span class=\"header_key\" style=\"width: 150px;text-align: left;\">" +
                                "Price" +
                                "</span>");
            printWriter.write("<input class=\"header_value\" name=\"newPrice\" value=\"1\" pattern=\"^[0-9]+\\.?[0-9]{0,2}$\">");
            printWriter.write("<br>");
            printWriter.write("<hr>");
            printWriter.write("</div>");
            printWriter.write("<hr>");
            printWriter.write("<button type=\"submit\" name=\"command\" value=\"" +
                                CREATE_PRODUCT_COMMAND +
                                "\">" +
                                "Ok" +
                                "</button>");
            printWriter.write("<button type=\"submit\" name=\"command\" value=\"" +
                                LIST_OF_PRODUCT_COMMAND +
                                "\">" +
                                "Cancel" +
                                "</button>");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("service finish");
    }
}
