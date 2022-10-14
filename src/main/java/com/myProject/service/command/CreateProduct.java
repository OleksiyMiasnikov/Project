package com.myProject.service.command;

import com.myProject.entitie.Product;
import com.myProject.entitie.Unit;
import com.myProject.service.CommodityExpertManager;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.myProject.util.Constants.LIST_OF_PRODUCT_COMMAND;

/**
 * Implementation of CREATE_PRODUCT_COMMAND
 */
public class CreateProduct implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(CreateProduct.class);

    /**
     * gets data from 'product_details.jsp'
     * if 'id' equals '0' creates new product else updates existed product
     * than pass control to 'command.list_of_products'
     *
     * @return LIST_OF_PRODUCT_COMMAND
     */

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("Start creating new product");
        String name = req.getParameter("newName");
        if ("".equals(name)) {
            return "controller?command=" + LIST_OF_PRODUCT_COMMAND;
        }
        CommodityExpertManager commodityExpertManager =
                (CommodityExpertManager) req.getServletContext()
                        .getAttribute("CommodityExpertManager");
        long id = 0L;
        double price = 0;
        String strId = req.getParameter("newId");
        String unit = req.getParameter("newUnit");
        String strPrice = req.getParameter("newPrice");
        if (strPrice != null && !strPrice.equals("")) {
            price = Double.parseDouble(strPrice);
        }
        if (strId != null && !strId.equals("")) {
            id = Long.parseLong(strId);
        }
        Product newProduct = Product.builder()
                .id(id)
                .name(name)
                .unit(Unit.valueOf(unit))
                .price(price)
                .build();
        if (id == 0L) {
            if (commodityExpertManager.create(newProduct) != null) {
                logger.info(name + " added");
            } else {
                logger.info("Unable to add " + name);
            }
        } else {
            commodityExpertManager.update(newProduct);
            logger.info(name + " updated");
        }
        return "controller?command=command.list_of_products";
    }
}
