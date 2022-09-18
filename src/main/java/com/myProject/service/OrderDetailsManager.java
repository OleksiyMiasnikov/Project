package com.myProject.service;

import com.myProject.dao.OrderDetailsDao;
import com.myProject.dao.entitie.OrderDetails;
import com.myProject.exception.DaoException;
import com.myProject.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class OrderDetailsManager {
    private static final Logger logger = (Logger) LogManager.getLogger(OrderDetailsManager.class);
    private static OrderDetailsManager instance;
    private final OrderDetailsDao orderDetailsDao;

    public static OrderDetailsManager getInstance(OrderDetailsDao orderDetailsDao) {
        if (instance == null) {
            instance = new OrderDetailsManager(orderDetailsDao);
            logger.info("Instance of OrderDetailsDao created" + instance);
        }
        return instance;
    }
    private OrderDetailsManager(OrderDetailsDao orderDetailsDao) {
        this.orderDetailsDao = orderDetailsDao;
    }

    public List<OrderDetails> findAll() throws DaoException {
        logger.info("Start finding all OrderDetails");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            List<OrderDetails> orderDetailsList = orderDetailsDao.findAll(con);
            logger.info("Finish finding all OrderDetails");
            return orderDetailsList;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public Long getId(String orderDetails) throws DaoException {
        logger.info("Start getting id of " + orderDetails);
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            long id = orderDetailsDao.findByName(con, orderDetails).getId();
            logger.info(orderDetails + " has id: " + id);
            return id;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public List<OrderDetails> detailsByOrderId(long id) throws DaoException {
        logger.info("Start getting details of order #" + id);
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return orderDetailsDao.detailsByOrderId(con, id);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }
}
