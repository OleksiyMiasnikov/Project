package com.myProject.service;

import com.myProject.dao.entitie.Warehouse;
import com.myProject.dao.WarehouseDao;
import com.myProject.exception.DaoException;
import com.myProject.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class WarehouseManager {
    private static final Logger logger = (Logger) LogManager.getLogger(WarehouseManager.class);
    private static WarehouseManager instance;
    private final WarehouseDao warehouseDao;

    public static WarehouseManager getInstance(WarehouseDao warehouseDao) {
        if (instance == null) {
            instance = new WarehouseManager(warehouseDao);
            logger.info("Instance of WarehouseDao created");
        }
        return instance;
    }
    private WarehouseManager(WarehouseDao warehouseDao) {
        this.warehouseDao = warehouseDao;
    }


    public List<Warehouse> findAll() throws DaoException {
        logger.info("Start finding all products in warehouse");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return warehouseDao.findAll(con);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }
}
