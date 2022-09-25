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

    public static synchronized CashierManager getInstance(OrderDao orderDao, OrderDetailsDao orderDetailsDao) {
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
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            con.setAutoCommit(false);
            DaoFactory.getInstance().getWarehouseDao().recoveryAfterDeletingOrder(con, id);
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
            return orderDetailsDao.readByOrderId(con, id);
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
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            con.setAutoCommit(false);
            boolean isOffTaken = DaoFactory.getInstance().getWarehouseDao().takeOffProduct(con, orderDetails);
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
            logger.error("Unable to create order details. Rollback.");
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

    public void deleteAll(String[] orders) throws DaoException {
        for (String order: orders) {
            deleteOrder(Long.parseLong(order));
        }
    }

    public void deleteProductsInOrder(String strId, String[] orderDetailsArray) throws DaoException {
        // 1. product loop
        // 1.1 in loop - increase product in warehouse by product_id from OrderDetails
        // 1.2 delete OrderDetails by id
        // 2. if order does not have orderDetails, delete order
        // 3. update total amount in order
        // !!! transactions
        long orderId = Long.parseLong(strId);
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            con.setAutoCommit(false);
            for (String orderDetailsId : orderDetailsArray) {
                logger.info(orderDetailsId);
                OrderDetails orderDetails = orderDetailsDao.read(con, Long.parseLong(orderDetailsId));
                DaoFactory.getInstance().getWarehouseDao()
                        .updateQuantity(con,
                                orderDetails.getQuantity(),
                                orderDetails.getProduct().getId());
                con.commit();
                orderDetailsDao.delete(con, orderDetails.getId());
                con.commit();
            }
            if (orderDetailsDao.readByOrderId(con, orderId).isEmpty()) {
                logger.info("Order is empty");
                orderDao.delete(con, orderId);
                con.commit();
            }
        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Connection is null.  " + ex);
            }
            logger.error("Unable to delete products in order. Rollback.");
            throw new DaoException("Unable to delete products in order. Rollback. " + e);
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
