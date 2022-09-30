package com.myProject.service.command;

import com.myProject.dao.entitie.Order;
import com.myProject.dao.entitie.OrderDetails;
import com.myProject.service.CashierManager;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ServeOrder implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ServeOrder.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("--- ServeOrder ---");

        String strId = req.getParameter("id");
        long id = Long.parseLong(strId);
        CashierManager manager =
                (CashierManager) req.getSession()
                        .getServletContext()
                        .getAttribute("CashierManager");
        Order order = manager.read(id);
        List<OrderDetails> orderDetailsList = manager.detailsByOrderId(id);
        req.setAttribute("orderDetails", orderDetailsList);
        req.setAttribute("order", order);
        return "order.jsp";



/*        String strId = req.getParameter("selectedProduct");
        Product product = new Product(0L,"", Unit.valueOfLabel("шт"), 1d);
        if (strId != null) {
            CommodityExpertManager manager =
                    (CommodityExpertManager) req.getSession().getServletContext().getAttribute("CommodityExpertManager");
            product = manager.read(Long.parseLong(strId));
        }
        req.getSession().setAttribute("units", Unit.values());
        req.getSession().setAttribute("result", product);
        req.getSession().setAttribute("Fragment", "/ProductFragment");
        //return MAIN_PAGE;
        return "product_details.jsp";*/
    }
}
