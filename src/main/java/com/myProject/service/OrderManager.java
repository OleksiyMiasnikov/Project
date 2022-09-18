package com.myProject.service;

import com.myProject.dao.OrderDao;
import com.myProject.dao.RoleDao;
import com.myProject.dao.entitie.Order;
import com.myProject.dao.entitie.Role;
import com.myProject.exception.DaoException;
import com.myProject.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class OrderManager {
    private static final Logger logger = (Logger) LogManager.getLogger(OrderManager.class);
    private static OrderManager instance;
    private final OrderDao orderDao;

    public static OrderManager getInstance(OrderDao orderDao) {
        if (instance == null) {
            instance = new OrderManager(orderDao);
            logger.info("Instance of OrderDao created");
        }
        return instance;
    }
    private OrderManager(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<Order> findAll() throws DaoException {
        logger.info("Start finding all orders");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            List<Order> ordersList = orderDao.findAll(con);
            logger.info("Finish finding all orders");
            return ordersList;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public Long getId(String order) throws DaoException {
        logger.info("Start getting id of " + order);
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            long id = orderDao.findByName(con, order).getId();
            logger.info(order + " has id: " + id);
            return id;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public Order read(long id) throws DaoException {
        logger.info("Start getting order by " + id);
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return orderDao.read(con, id);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }
}
