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
import java.util.Arrays;
import java.util.List;

import static com.myProject.util.Constants.LIST_OF_PRODUCT_COMMAND;
import static com.myProject.util.Constants.MAIN_PAGE;

public class ListOfProducts implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ListOfProducts.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("Start execute command -ListOfProducts-");
        String strPage = req.getParameter("page");
        int currentPage = 1;
        if (strPage != null && !"".equals(strPage)) currentPage = Integer.parseInt(strPage);
        CommodityExpertManager manager =
                (CommodityExpertManager) req.getServletContext().getAttribute("CommodityExpertManager");
        int total = manager.findRowsTotal();
        int pagesTotal = (int)Math. ceil(total/10d);
        List<Product> productList = manager.findAllProducts((currentPage - 1) * 10, 10);

        req.getSession().setAttribute("result", productList);
        req.getSession().setAttribute("page", currentPage);
        req.getSession().setAttribute("pages_total", pagesTotal);
        req.getSession().setAttribute("command_name", LIST_OF_PRODUCT_COMMAND);
        req.getSession().setAttribute("Fragment", "/ProductListFragment");
        return MAIN_PAGE;
    }
}
