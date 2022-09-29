package com.myProject.service.command;

import com.myProject.dao.entitie.Warehouse;
import com.myProject.service.exception.DaoException;
import com.myProject.service.CommodityExpertManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.myProject.util.Constants.REMAINS_COMMAND;

public class Remains implements Command {
    private double amountTotal;
    private double quantityTotal;
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        CommodityExpertManager manager =
                (CommodityExpertManager) req
                        .getSession()
                        .getServletContext()
                        .getAttribute("CommodityExpertManager");
        String strPage = req.getParameter("page");
        int currentPage = 1;
        if (strPage != null && !"".equals(strPage)) currentPage = Integer.parseInt(strPage);
        int pagesTotal = (int)Math. ceil(manager.findRowsTotalInWarehouse()/10d);
        List<Warehouse> list = manager.findAll((currentPage - 1) * 10, 10);
        CalculateWarehouseTotal(list);
        req.getSession().setAttribute("amountTotal", amountTotal);
        req.getSession().setAttribute("quantityTotal", quantityTotal);
        req.getSession().setAttribute("result", list);
        req.getSession().setAttribute("page", currentPage);
        req.getSession().setAttribute("pages_total", pagesTotal);
        req.getSession().setAttribute("command_name", REMAINS_COMMAND);
        return "warehouse_list.jsp";
    }

    private void CalculateWarehouseTotal(List<Warehouse> list) {
        if (list == null) return;
        quantityTotal = 0;
        amountTotal = 0;
        for (Warehouse element : list) {
            quantityTotal += element.getQuantity();
            amountTotal += element.getQuantity() * element.getProduct().getPrice();
        }
    }
}
