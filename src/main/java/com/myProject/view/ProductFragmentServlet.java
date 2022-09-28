package com.myProject.view;

import com.myProject.dao.entitie.Product;
import com.myProject.dao.entitie.Unit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.myProject.util.Constants.*;

@WebServlet("/ProductFragment")
public class ProductFragmentServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(ProductFragmentServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("service start");
        Product product = (Product) req.getSession().getAttribute("result");
        String strId = req.getParameter("selectedProduct");
        String title = "*** New product ***";

        if (strId != null)  title = "*** Update product ***";
        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write("<span style=\"font-size: 8px;\">" +
                                "[ProductFragmentServlet: /ProductFragment" +
                                "</span>");
            printWriter.write("<p style=\"text-align: center; font-size: 22px;font-weight: bold\">" +
                                title +
                                "</p>");
            printWriter.write("<hr>");
            if (strId != null) {
                printWriter.write("<span class=\"header_key\">" +
                        "Id" +
                        "</span>");
                printWriter.write("<input class=\"header_value\" name=\"newId\" value=\"" +
                        product.getId() +
                        "\">");
                printWriter.write("<br>");
            }
            printWriter.write("<span class=\"header_key\">" +
                                "Product name" +
                                "</span>");
            printWriter.write("<input class=\"header_value\" name=\"newName\" value=\"" +
                                product.getName() +
                                "\">");
            printWriter.write("<br>");
            printWriter.write("<span class=\"header_key\">" +
                                "Unit" +
                                "</span>");

            printWriter.write("<select class=\"header_value\" name=\"newUnit\" value=\"" +
                                product.getUnit().getLabelUa() +
                                "\">");
            printWriter.write("<option>" +
                                product.getUnit().getLabelUa() +
                                "</option>");
            for (Unit unit: Unit.values()) {
                printWriter.write("<option value=\"" +
                                    unit.labelUa +
                                    "\">");
                printWriter.write(unit.labelUa);
                printWriter.write("</option>");
            }
            printWriter.write("</select>");
            printWriter.write("<br>");

            printWriter.write("<span class=\"header_key\">" +
                                "Price" +
                                "</span>");
            printWriter.write("<input class=\"header_value\" name=\"newPrice\" value=\"" +
                                product.getPrice() +
                                "\" pattern=\"^[0-9]+\\.?[0-9]{0,2}$\">");
            printWriter.write("<br>");
            printWriter.write("<hr>");
            printWriter.write("<p>");
            printWriter.write("<button class=\"submit_button\" type=\"submit\" name=\"command\" value=\"" +
                                CREATE_PRODUCT_COMMAND +
                                "\">" +
                                "Ok" +
                                "</button>");
            printWriter.write("<button class=\"submit_button\" type=\"submit\" name=\"command\" value=\"" +
                                LIST_OF_PRODUCT_COMMAND +
                                "\">" +
                                "Cancel" +
                                "</button>");
            printWriter.write("</p>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("service finish");
    }
}
