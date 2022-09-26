package com.myProject.service.command;

import com.myProject.dao.entitie.Warehouse;
import com.myProject.service.exception.DaoException;
import com.myProject.service.CommodityExpertManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.myProject.util.Constants.MAIN_PAGE;

public class Remains implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        //new CommodityExpert(new User()).initWindow(req, resp);
        CommodityExpertManager commodityExpertManager =
                (CommodityExpertManager) req
                        .getSession()
                        .getServletContext()
                        .getAttribute("CommodityExpertManager");
        List<Warehouse> warehouseList = commodityExpertManager.findAll();
        req.getSession().setAttribute("result", warehouseList);
        req.getSession().setAttribute("Fragment", "/WarehouseFragment");
        return MAIN_PAGE;
    }
}
