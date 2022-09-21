package com.myProject.employee;

import com.myProject.dao.entitie.User;
import com.myProject.dao.entitie.Warehouse;
import com.myProject.exception.DaoException;
import com.myProject.service.WarehouseManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.myProject.util.Constants.*;

public class CommodityExpert extends Employee {
    private static final Logger logger = (Logger) LogManager.getLogger(CommodityExpert.class);

    public CommodityExpert(User user) {
        super(user, COMMAND_NEW_PRODUCT, COMMAND_LIST_OF_PRODUCT, COMMAND_INCOME, COMMAND_REPORTS);
    }

    @Override
    public void initWindow(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        logger.info("initWindow start");
        WarehouseManager warehouseManager =
                (WarehouseManager) req
                        .getSession()
                        .getServletContext()
                        .getAttribute("WarehouseManager");
        List<Warehouse> warehouseList = warehouseManager.findAll();
        req.setAttribute("result", warehouseList);
        req.setAttribute("Fragment", "/WarehouseFragment");
        logger.info(Arrays.toString(warehouseList.toArray()));
        try {
            req.getRequestDispatcher("jsp/mainWindow.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

