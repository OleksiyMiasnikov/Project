package com.myProject.dao.mysql;

import com.myProject.dao.ProductDao;
import com.myProject.dao.OrderDetailsDao;
import com.myProject.dao.UserDao;
import com.myProject.dao.entitie.Order;
import com.myProject.dao.entitie.OrderDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myProject.util.Constants.*;


public class OrderDetailsDaoImpl implements OrderDetailsDao {

    /**
     * reads one record from table `order_details` by field `id`
     * @param con - connection from connection pool
     * @param id -  id of searched field
     * @return - object OrderDetails with searched record data or @null if it is absent
     * @throws SQLException
     */
    @Override
    public OrderDetails read(Connection con, Long id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = con.prepareStatement(READ_ORDER_DETAILS_BY_ID);
            pstmt.setLong(1, id);
            pstmt.executeQuery();
            resultSet = pstmt.getResultSet();
            if (resultSet.next()) {
                ProductDao productDao = DaoFactoryImpl.getInstance().getProductDao();
                Order order = DaoFactoryImpl.getInstance().getOrderDao().read(con, id);
                return buildOrderDetails(con, productDao, order, resultSet);
            } else {
                return null;
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
    }

    /**
     * creates record in table `order_details` with data from object @entity
     * @param con - connection from connection pool
     * @param entity
     * @return
     * @throws SQLException
     */
    @Override
    public OrderDetails create(Connection con, OrderDetails entity) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = con.prepareStatement(CREATE_ORDER_DETAILS, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, entity.getOrder().getId());
            pstmt.setLong(2, entity.getProduct().getId());
            pstmt.setDouble(3, entity.getQuantity());
            pstmt.setDouble(4, entity.getPrice());
            pstmt.executeUpdate();
            resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
                return entity;
            } else {
                return null;
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
    }

    @Override
    public List<OrderDetails> readByOrderId(Connection con, int from, int size, long id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(READ_ORDER_DETAILS_BY_ORDER_ID);
            pstmt.setLong(1, id);
            pstmt.setInt(2, from);
            pstmt.setInt(3, size);
            pstmt.executeQuery();
            resultSet = pstmt.getResultSet();
            ProductDao productDao = DaoFactoryImpl.getInstance().getProductDao();
            Order order = DaoFactoryImpl.getInstance().getOrderDao().read(con, id);
            while (resultSet.next()) {
                orderDetailsList.add(buildOrderDetails(con, productDao, order, resultSet));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
        Collections.sort(orderDetailsList);
        return orderDetailsList;
    }

    @Override
    public boolean delete(Connection con, Long id) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(DELETE_ORDER_DETAILS)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            return true;
        }
    }

    @Override
    public boolean deleteByOrderId(Connection con, long id) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(DELETE_ORDER_DETAILS_BY_ORDER_ID)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            return true;
        }
    }

    @Override
    public int findRowsTotal(Connection con, long id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = con.prepareStatement(COUNT_ROWS_IN_ORDER_DETAILS);
            pstmt.setLong(1, id);
            pstmt.execute();
            resultSet = pstmt.getResultSet();
            resultSet.next();
            return resultSet.getInt("rows_total");
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
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

    @Override
    public List<OrderDetails> findAll(Connection con, int from, int size){
        return null;
    }
    @Override
    public OrderDetails findByName(Connection con, String name){
        return null;
    }
    @Override
    public void update(Connection con, OrderDetails entity) {

    }
}
