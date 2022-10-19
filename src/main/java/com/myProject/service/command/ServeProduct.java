package com.myProject.service.command;

import com.myProject.entitie.Product;
import com.myProject.entitie.Unit;
import com.myProject.service.CommodityExpertManager;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.myProject.util.Constants.PATH;
/**
 * Implementation of
 */
/**
 *  prepares data for 'product_details.jsp'
 *  read product from database if 'id' is not equal '0'
 *  else set default data
 */
public class ServeProduct implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ServeProduct.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        logger.info("Serve product");
        Product product;
        String strId = req.getParameter("selectedProduct");
        if (strId != null) {
            CommodityExpertManager manager =
                    (CommodityExpertManager) req.getServletContext().getAttribute("CommodityExpertManager");
            product = manager.read(Long.parseLong(strId));
        } else {
            product = Product.builder()
                    .id(0L)
                    .name("")
                    .unit(Unit.PCS)
                    .price(1d)
                    .build();
        }
        req.getSession().setAttribute("units", Unit.values());
        req.getSession().setAttribute("result", product);
        req.getSession().setAttribute("title", "command.update_product");
        return PATH + "product_details.jsp";
    }
}
