package com.myProject.service.command;

import com.myProject.employee.Employee;
import com.myProject.service.CommodityExpertManager;
import com.myProject.entitie.Product;
import com.myProject.entitie.Unit;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static com.myProject.util.Constants.*;

/**
 * Implementation of NEW_PRODUCT_COMMAND
 */

public class ServeProduct implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ServeProduct.class);

    /**
     *  prepares data for 'product_details.jsp'
     *  reads product from the database if 'id' is not equal '0'
     *  else set default data
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        logger.info("Serve product");
        Product product;
        String strId = req.getParameter("selectedProduct");
        HttpSession session = req.getSession();
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
        Employee.manuUp(session, List.of(CREATE_PRODUCT_COMMAND, BACK_COMMAND));
        session.setAttribute("units", Unit.values());
        session.setAttribute("result", product);
        session.setAttribute("title", "command.update_product");
        return PATH + "product_details.jsp";
    }
}
