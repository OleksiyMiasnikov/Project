package com.myProject.employee;

import com.myProject.dao.entitie.Goods;
import com.myProject.dao.entitie.User;
import com.myProject.dao.entitie.Warehouse;
import com.myProject.exception.DaoException;
import com.myProject.service.GoodsManager;
import com.myProject.service.WarehouseManager;
import com.myProject.view.WarehouseWindow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.myProject.dao.Constants.*;

public class CommodityExpert extends Employee {
    private static final Logger logger = (Logger) LogManager.getLogger(CommodityExpert.class);

    public CommodityExpert(User user) {
        super(user, COMMAND_NEW_GOODS, COMMAND_LIST_OF_GOODS, COMMAND_INCOME, COMMAND_REPORTS);
    }

    @Override
    public void initWindow(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        WarehouseManager warehouseManager =
                (WarehouseManager) req
                        .getSession()
                        .getServletContext()
                        .getAttribute("WarehouseManager");
        List<Warehouse> warehouseList = warehouseManager.findAll();
        req.setAttribute("result", warehouseList);
        req.setAttribute("Window", "jsp/commodityExpertWindow.jsp");
        logger.info(Arrays.toString(warehouseList.toArray()));
        try {
            req.getRequestDispatcher("WarehouseWindow").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

