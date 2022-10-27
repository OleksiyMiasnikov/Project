package com.myProject.service.command;

import com.myProject.service.CommodityExpertManager;
import com.myProject.entitie.Product;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.myProject.util.Constants.*;
/**
 * Implementation of LIST_OF_PRODUCT_COMMAND
 */
public class ListOfProducts implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ListOfProducts.class);

    /**
     * prepares data for page 'product_list.jsp'
     * checks number of current page in pagination,
     * determines total number of pages
     * and invokes DAO method to make appropriate selection in database
     * puts result into session attribute
     *
     * @return relative address of 'product_list.jsp'
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, AppException {
        logger.info("LIST_OF_PRODUCT_COMMAND executed");
        CommodityExpertManager manager =
                (CommodityExpertManager) req.getServletContext().getAttribute("CommodityExpertManager");
        int currentPage = 1;
        String strPage = req.getParameter("page");
        if (strPage != null &&
                !"".equals(strPage) &&
                LIST_OF_PRODUCT_COMMAND.equals(req.getSession().getAttribute("command_name"))) {
            currentPage = Integer.parseInt(strPage);
        }
        int pagesTotal = (int)Math. ceil(manager.findRowsTotalInProduct()/10d);
        List<Product> productList = manager.findAllProducts((currentPage - 1) * 10, 10);
        req.getSession().setAttribute("result", productList);
        req.getSession().setAttribute("page", currentPage);
        req.getSession().setAttribute("pages_total", pagesTotal);
        req.getSession().setAttribute("command_name", LIST_OF_PRODUCT_COMMAND);
        req.getSession().setAttribute("title", "command.list_of_products");
        return PATH + "product_list.jsp";
    }
}
