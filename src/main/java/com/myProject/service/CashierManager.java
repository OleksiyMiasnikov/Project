package com.myProject.service;

import com.myProject.dao.DaoFactory;
import com.myProject.dao.OrderDao;
import com.myProject.dao.OrderDetailsDao;
import com.myProject.dao.entitie.Order;
import com.myProject.dao.entitie.OrderDetails;
import com.myProject.exception.DaoException;
import com.myProject.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CashierManager {
    private static final Logger logger = (Logger) LogManager.getLogger(CashierManager.class);
    private static CashierManager instance;
    private final OrderDao orderDao;
    private final OrderDetailsDao orderDetailsDao;

    public static CashierManager getInstance(OrderDao orderDao, OrderDetailsDao orderDetailsDao) {
        if (instance == null) {
            instance = new CashierManager(orderDao, orderDetailsDao);
            logger.info("Instance of CashierManager created");
        }
        return instance;
    }

    private CashierManager(OrderDao orderDao, OrderDetailsDao orderDetailsDao) {
        this.orderDao = orderDao;
        this.orderDetailsDao = orderDetailsDao;
    }

    public List<Order> findAllOrders() throws DaoException {
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

    public Long getOrderId(String order) throws DaoException {
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

    public Order createOrder(Order newOrder) throws DaoException {
        logger.info("Start creating order");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return orderDao.create(con, newOrder);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public void updateTotal(long id) throws DaoException {
        logger.info("Start updating total amount");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            orderDao.updateTotal(con, id);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public void deleteOrder(long id) throws DaoException {
        logger.info("Start deleting order");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);
            orderDetailsDao.deleteByOrderId(con, id);
            con.commit();
            orderDao.delete(con, id);
            con.commit();
        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Connection is null.  " + ex);
            }
            logger.warn("Unable to delete order: " + id + ". Rollback.");
            throw new DaoException("Unable to delete order: " + id + ". Rollback. " + e);
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public List<OrderDetails> findAllOrderDetails() throws DaoException {
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

    public Long getOrderDetailsId(String orderDetails) throws DaoException {
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

    public OrderDetails createOrderDetails(OrderDetails orderDetails) throws DaoException {
        logger.info("Start creating details of order #" + orderDetails.getOrder().getId());
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);
            boolean isOffTaken;
            isOffTaken = DaoFactory.getInstance().getWarehouseDao().offTakeProduct(con, orderDetails);
            con.commit();
            if (isOffTaken) {
                OrderDetails result = orderDetailsDao.create(con, orderDetails);
                con.commit();
                return result;
            } else {
                con.rollback();
                return null;
            }
        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Connection is null.  " + ex);
            }
            logger.warn("Unable to create order details. Rollback.");
            throw new DaoException("Unable to create order details. Rollback. " + e);
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

}
