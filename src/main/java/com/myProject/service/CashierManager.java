package com.myProject.service;

import com.myProject.dao.DaoFactory;
import com.myProject.dao.OrderDao;
import com.myProject.dao.OrderDetailsDao;
import com.myProject.dao.entitie.Order;
import com.myProject.dao.entitie.OrderDetails;
import com.myProject.service.exception.DaoException;
import com.myProject.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    public List<Order> findAll(int from, int size, String direction) throws DaoException {
        logger.info("Start finding all orders or incomes");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            if ("IN".equals(direction)) {
                return orderDao.findAllIncomes(con, from, size);
            }
            return orderDao.findAll(con, from, size);
        } catch (SQLException e) {
            throw new DaoException("Cannot find all orders", e);
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
        } catch (SQLException e) {
            throw new DaoException("Cannot read order by id: " + id , e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public Order createOrder(Order newOrder, String direction) throws DaoException {
        logger.info("Start creating order");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return orderDao.create(con, newOrder, direction);
        } catch (SQLException e) {
            throw new DaoException("Cannot create order", e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public OrderDetails createOrderDetails(OrderDetails orderDetails, String direction) throws DaoException {
        logger.info("Start creating details of order #" + orderDetails.getOrder().getId());
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            con.setAutoCommit(false);
            int sign = 1;
            if ("OUT".equals(direction)) sign = -1;
            DaoFactory.getInstance().getWarehouseDao().updateQuantity(con,
                    sign * orderDetails.getQuantity(),
                    orderDetails.getProduct().getId());
            OrderDetails result = orderDetailsDao.create(con, orderDetails);
            con.commit();
            return result;
        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                logger.error("Connection is null.  " + ex);
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

    public void updateTotal(long id) throws DaoException {
        logger.info("Start updating total amount");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            orderDao.updateTotal(con, id);
        } catch (SQLException e) {
            throw new DaoException("Cannot update total amount in order " + id, e);
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
                logger.error("Connection is null.  " + ex);
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

    public List<OrderDetails> detailsByOrderId(long id) throws DaoException {
        logger.info("Start getting details of order #" + id);
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return orderDetailsDao.readByOrderId(con, 0, 1000, id);
        } catch (SQLException e) {
            throw new DaoException("Cannot getting details of order #" + id, e);
        } finally {
            try {
                if (con != null) con.close();
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

    public boolean deleteProductsInOrder(String strId, String[] orderDetailsArray) throws DaoException {
        // 1. product loop
        // 1.1 in loop - increase product in warehouse by product_id from OrderDetails
        // 1.2 delete OrderDetails by id
        // 2. if order does not have orderDetails, delete order
        // 3. update total amount in order
        // !!! transactions
        logger.info("Start deleting product from 'order_details'");
        long orderId = Long.parseLong(strId);
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            con.setAutoCommit(false);
            for (String orderDetailsId : orderDetailsArray) {
                OrderDetails orderDetails =
                        orderDetailsDao.read(con, Long.parseLong(orderDetailsId));
                DaoFactory.getInstance().getWarehouseDao()
                        .updateQuantity(con,
                                orderDetails.getQuantity(),
                                orderDetails.getProduct().getId());
                orderDetailsDao.delete(con, orderDetails.getId());
            }
            con.commit();
            if (orderDetailsDao.readByOrderId(con, 0, 1000, orderId).isEmpty()) {
                logger.info("Order is empty");
                orderDao.delete(con, orderId);
                con.commit();
                return true;
            }
            return false;
        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                logger.info("Connection is null.  " + ex);
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

    public int findRowsTotal(String direction) throws DaoException {
        logger.info("Start calculation quantity of rows in table 'order'");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return orderDao.findRowsTotal(con, direction);
        } catch (SQLException e) {
            throw new DaoException("Unable to determine quantity of '' rows in table 'order'", e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public Map<String, Double> ordersTotals(String direction) throws DaoException {
        logger.info("Starting determining orders totals");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return orderDao.Totals(con, direction);
        } catch (SQLException e) {
            throw new DaoException("Unable to determine orders totals", e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public double findRowsTotalInOrderDetails(long id) throws DaoException {
        logger.info("Start calculation quantity of rows in table 'order_details'");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return orderDetailsDao.findRowsTotal(con, id);
        } catch (SQLException e) {
            throw new DaoException("Unable to determine quantity of '' rows in table 'order'", e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public List<OrderDetails> findAllOrderDetails(int from, int size, long id) throws DaoException {
        logger.info("Start finding all orders or incomes");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return orderDetailsDao.readByOrderId(con, from, size, id);
        } catch (SQLException e) {
            throw new DaoException("Cannot find all orders", e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }


/*    public Order createIncome(Order currentOrder) throws DaoException {
        logger.info("Start creating income");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return orderDao.createIncome(con, currentOrder);
        } catch (SQLException e) {
            throw new DaoException("Unable creating income" + e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

        //Deprecated
    public OrderDetails createIncomeDetails(OrderDetails orderDetails) throws DaoException {
        logger.info("Start creating details of income #" + orderDetails.getOrder().getId());
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            con.setAutoCommit(false);
            DaoFactory.getInstance().getWarehouseDao().updateQuantity(con, orderDetails.getQuantity(),orderDetails.getProduct().getId());
            OrderDetails result = orderDetailsDao.create(con, orderDetails);
            con.commit();
            return result;
        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                logger.error("Connection is null.  " + ex);
            }
            logger.error("Unable to create income details. Rollback.");
            throw new DaoException("Unable to create income details. Rollback. " + e);
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
    */
}
