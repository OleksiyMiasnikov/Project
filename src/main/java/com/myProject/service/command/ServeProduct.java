package com.myProject.service.command;

import com.myProject.dao.entitie.Product;
import com.myProject.dao.entitie.Unit;
import com.myProject.service.CommodityExpertManager;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.myProject.util.Constants.MAIN_PAGE;

public class ServeProduct implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ServeProduct.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("--- ServeProduct ---");
        String strId = req.getParameter("selectedProduct");
        Product product = new Product(0L,"", Unit.valueOfLabel("шт"), 1d);
        if (strId != null) {
            CommodityExpertManager manager =
                    (CommodityExpertManager) req.getSession().getServletContext().getAttribute("CommodityExpertManager");
            product = manager.read(Long.parseLong(strId));
        }
        req.getSession().setAttribute("units", Unit.values());
        req.getSession().setAttribute("result", product);
        req.getSession().setAttribute("Fragment", "/ProductFragment");
        return MAIN_PAGE;
    }
}
