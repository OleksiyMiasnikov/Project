package com.myProject.dao.mysql;

import com.myProject.dao.ProductDao;
import com.myProject.dao.OrderDetailsDao;
import com.myProject.dao.entitie.Order;
import com.myProject.dao.entitie.OrderDetails;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myProject.util.Constants.*;


public class OrderDetailsDaoImpl implements OrderDetailsDao {
    private static final Logger logger = (Logger) LogManager.getLogger(OrderDetailsDaoImpl.class);

    /**
     * reads one record from table `order_details` by field `id`
     * @param con - connection from connection pool
     * @param id -  id of searched field
     * @return - object OrderDetails with searched record data or @null if it is absent
     * @throws DaoException
     */

    @Override
    public OrderDetails read(Connection con, Long id) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(READ_ORDERDETAILS_BY_ID)) {
            pstmt.setLong(1, id);
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();
            if (resultSet.next()) {
                ProductDao productDao = DaoFactoryImpl.getInstance().getProductDao();
                Order order = DaoFactoryImpl.getInstance().getOrderDao().read(con, id);
                return buildOrderDetails(con, productDao, order, resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Unable to find user! " + e);
            throw new DaoException("Unable to find user! ", e);
        }
    }

    /**
     * creates record in table `order_details` with data from object @entity
     * @param con - connection from connection pool
     * @param entity
     * @return
     * @throws DaoException
     */
    @Override
    public OrderDetails create(Connection con, OrderDetails entity) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(CREATE_ORDER_DETAILS, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, entity.getOrder().getId());
            pstmt.setLong(2, entity.getProduct().getId());
            pstmt.setDouble(3, entity.getQuantity());
            pstmt.setDouble(4, entity.getPrice());
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
                return entity;
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Unable to createOrder order! " + e);
            throw new DaoException("Unable to createOrder order! ", e);
        }
    }

    @Override
    public void update(Connection con, OrderDetails entity) throws DaoException {

    }

    @Override
    public boolean delete(Connection con, Long id) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(DELETE_ORDERDETAILS)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderDetails> findAll(Connection con) throws DaoException {
        return null;
    }

    @Override
    public OrderDetails findByName(Connection con, String name) throws DaoException {
        return null;
    }

    @Override
    public List<OrderDetails> readByOrderId(Connection con, long id) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(READ_ORDER_DETAILS_BY_ORDER_ID)) {
            pstmt.setLong(1, id);
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();
            List<OrderDetails> orderDetailsList = new ArrayList<>();
            ProductDao productDao = DaoFactoryImpl.getInstance().getProductDao();
            Order order = DaoFactoryImpl.getInstance().getOrderDao().read(con, id);
            while (resultSet.next()) {
                orderDetailsList.add(buildOrderDetails(con, productDao, order, resultSet));
            }
            Collections.sort(orderDetailsList);
            resultSet.close();
            return orderDetailsList;
        } catch (SQLException e) {
            logger.error("Unable to find order details! " + e);
            throw new DaoException("Unable to find order details! ", e);
        }
    }

    @Override
    public boolean deleteByOrderId(Connection con, long id) {
        try (PreparedStatement pstmt = con.prepareStatement(DELETE_ORDER_DETAILS_BY_ORDER_ID)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private OrderDetails buildOrderDetails(Connection con, ProductDao productDao, Order order, ResultSet resultSet) throws SQLException {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(resultSet.getLong(1));
        orderDetails.setOrder(order);
        orderDetails.setProduct(productDao.read(con, resultSet.getLong(3)));
        orderDetails.setQuantity(resultSet.getInt(4));
        orderDetails.setPrice(resultSet.getDouble(5));
        return orderDetails;
    }
}
