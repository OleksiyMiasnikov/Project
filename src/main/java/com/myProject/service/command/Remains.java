package com.myProject.service.command;

import com.myProject.entitie.Warehouse;
import com.myProject.service.exception.DaoException;
import com.myProject.service.CommodityExpertManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.myProject.util.Constants.*;

public class Remains implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        CommodityExpertManager manager =
                (CommodityExpertManager) req
                        .getSession()
                        .getServletContext()
                        .getAttribute("CommodityExpertManager");
        String strPage = req.getParameter("page");
        int currentPage = 1;
        if (strPage != null &&
            !"".equals(strPage) &&
            REMAINS_COMMAND.equals(req.getSession().getAttribute("command_name"))) {
            currentPage = Integer.parseInt(strPage);
        }
        int pagesTotal = (int)Math. ceil(manager.findRowsTotalInWarehouse()/10d);
        List<Warehouse> list = manager.findAll((currentPage - 1) * 10, 10);
        Map<String, Double> totals = manager.WarehouseTotals();
        req.getSession().setAttribute("total_amount", totals.get("total_amount"));
        req.getSession().setAttribute("total_quantity", totals.get("total_quantity"));
        req.getSession().setAttribute("result", list);
        req.getSession().setAttribute("page", currentPage);
        req.getSession().setAttribute("pages_total", pagesTotal);
        req.getSession().setAttribute("command_name", REMAINS_COMMAND);
        req.getSession().setAttribute("title", "command.remains");
        return PATH + "warehouse_list.jsp";
    }
}
