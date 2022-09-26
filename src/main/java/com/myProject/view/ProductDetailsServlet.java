package com.myProject.view;

import com.myProject.dao.entitie.Product;
import com.myProject.dao.entitie.Unit;
import com.myProject.service.exception.DaoException;
import com.myProject.service.CommodityExpertManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.myProject.util.Constants.COMMAND_LIST_OF_PRODUCT;
import static com.myProject.util.Constants.COMMAND_SHOW_USERS;

@WebServlet("/serveProduct")
public class ProductDetailsServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(ProductDetailsServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if ("Cancel".equals(req.getParameter("button"))) {
            logger.info("'Cansel' has pressed");
            resp.sendRedirect("controller?command=" + COMMAND_SHOW_USERS);
            return;
        }
        long id = 0L;
        double price = 0;
        String name = req.getParameter("newName");
        Unit unit = Unit.valueOfLabel(req.getParameter("newUnit"));

        String strPrice = req.getParameter("newPrice");
        if (strPrice != null && !strPrice.equals("")) {
            price = Double.parseDouble(strPrice);
        }

        String strId = req.getParameter("id");
        if (strId != null && !strId.equals("")) {
            id = Long.parseLong(strId);
        }

        try {
            CommodityExpertManager commodityExpertManager =
                    (CommodityExpertManager) req.getSession()
                    .getServletContext()
                    .getAttribute("CommodityExpertManager");

            Product newProduct = new Product(id, name, unit, price);
            if (id == 0L) {
                if (commodityExpertManager.create(newProduct) != null){
                    logger.info(name + " added");
                } else {
                    logger.info("Unable to add " + name);
                }
            } else {
                commodityExpertManager.update(newProduct);
                logger.info(name + " updated");
            }
            req.getRequestDispatcher("controller?command=" + COMMAND_LIST_OF_PRODUCT).forward(req, resp);
        } catch (DaoException | ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
