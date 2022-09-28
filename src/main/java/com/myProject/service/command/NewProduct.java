package com.myProject.service.command;

import com.myProject.service.CashierManager;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.myProject.util.Constants.MAIN_PAGE;

public class NewProduct implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(NewProduct.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("--- NewProduct ---");
        /*CashierManager cashierManager =
                (CashierManager) req.getSession().getServletContext().getAttribute("CashierManager");
        req.getSession().setAttribute("result", cashierManager.findAllIncomes());
        */
        req.getSession().setAttribute("Fragment", "/NewProductFragment");
        return MAIN_PAGE;
        //return "product_details.jsp";
    }
}
