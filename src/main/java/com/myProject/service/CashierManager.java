package com.myProject.service;

import com.myProject.dao.DaoFactory;
import com.myProject.dao.OrderDao;
import com.myProject.dao.OrderDetailsDao;
import com.myProject.dao.WarehouseDao;
import com.myProject.dto.Report;
import com.myProject.dto.ReportItem;
import com.myProject.entitie.Order;
import com.myProject.entitie.OrderDetails;
import com.myProject.entitie.Product;
import com.myProject.entitie.Warehouse;
import com.myProject.service.exception.DaoException;
import com.myProject.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
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
            return orderDao.findAll(con, direction, from, size);
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
                if (con != null) {
                    con.close();
                }
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
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public OrderDetails createOrderDetails(OrderDetails orderDetails, String direction) throws DaoException {
        logger.info("Start creating details of order #" + orderDetails.getOrder().getId());
        WarehouseDao warehouseDao = DaoFactory.getInstance().getWarehouseDao();
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            con.setAutoCommit(false);
            int sign = 1;
            if ("OUT".equals(direction)) sign = -1;
            if (warehouseDao.readByProduct(con, orderDetails.getProduct()) == null) {
                warehouseDao.create(con, Warehouse.builder()
                                                    .id(0L)
                                                    .quantity(orderDetails.getQuantity())
                                                    .product(orderDetails.getProduct())
                                                    .build());
            } else {
                warehouseDao.updateQuantity(con, sign * orderDetails.getQuantity(),
                        orderDetails.getProduct().getId());
            }
            OrderDetails result = orderDetailsDao.create(con, orderDetails);
            con.commit();
            return result;
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
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
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public void deleteOrder(long id, String direction) throws DaoException {
        logger.info("Start deleting order");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            con.setAutoCommit(false);
            DaoFactory.getInstance().getWarehouseDao().recoveryAfterDeletingOrder(con, id, direction);
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

    public void deleteAll(String[] orders) throws DaoException {
        for (String order: orders) {
            deleteOrder(Long.parseLong(order), "OUT");
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
            orderDao.updateTotal(con, orderId);
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
            return orderDao.totals(con, direction);
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

    public Report createReport(String typeOfReport) throws DaoException {
        logger.info("Start collecting data for report");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            con.setAutoCommit(false);
            List<ReportItem> list = orderDao.createReport(con);
            Date[] dates = orderDao.determineDates(con);
            if ("Z_report".equals(typeOfReport)) {
                orderDao.closeReportedOrders(con);
            }
            con.commit();
            return Report.builder()
                    .startDate(dates[0])
                    .endDate(dates[1])
                    .list(list)
                    .build();
        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                logger.info("Connection is null.  " + ex);
            }
            logger.error("Unable to collect data for report. Rollback.");
            throw new DaoException("Cannot collect data for report", e);
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

    public OrderDetails findOrderDetailByOrderAndProduct(Order order, Product product) throws DaoException {
        logger.info("Start finding order_detail by order and product");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return orderDetailsDao.readByOrderAndProduct(con, order, product);
        } catch (SQLException e) {
            throw new DaoException("Unable to find order_detail by order and product", e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public void updateOrderDetail(OrderDetails entity) throws DaoException {
        logger.info("Start updating quantity in order_detail");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            orderDetailsDao.update(con, entity);
        } catch (SQLException e) {
            throw new DaoException("Unable to update quantity in order_detail", e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }
}
