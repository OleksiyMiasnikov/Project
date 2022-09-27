package com.myProject.service.command;

import com.myProject.dao.entitie.Product;
import com.myProject.service.CommodityExpertManager;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.myProject.util.Constants.MAIN_PAGE;

public class ListOfProducts implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ListOfProducts.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("Start execute command -ListOfProducts-");
        CommodityExpertManager commodityExpertManager =
                (CommodityExpertManager) req.getServletContext().getAttribute("CommodityExpertManager");
        List<Product> productList = commodityExpertManager.findAllProducts();
        req.getSession().setAttribute("result", productList);
        req.getSession().setAttribute("Fragment", "/ProductListFragment");
        return MAIN_PAGE;
    }
}
