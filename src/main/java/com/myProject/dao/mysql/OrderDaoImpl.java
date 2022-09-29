package com.myProject.dao.mysql;

import com.myProject.dao.UserDao;
import com.myProject.dao.entitie.Order;
import com.myProject.dao.OrderDao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.myProject.util.Constants.*;

public class OrderDaoImpl implements OrderDao {
    @Override
    public Order read(Connection con, Long id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = con.prepareStatement(READ_ORDER_BY_ID);
            pstmt.setLong(1, id);
            pstmt.executeQuery();
            resultSet = pstmt.getResultSet();
            UserDao userDao = DaoFactoryImpl.getInstance().getUserDao();
            if (resultSet.next()) {
                return buildOrder(con, userDao, resultSet);
            } else {
                return null;
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
    }

    @Override
    public Order create(Connection con, Order entity) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = con.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, entity.getUser().getId());
            pstmt.setTimestamp(2, new Timestamp(entity.getDate().getTime()));
            pstmt.setDouble(3, entity.getTotalAmount());
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
    public List<Order> findAll(Connection con, int from, int size) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        List<Order> orderList = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(SELECT_ALL_ORDERS_WITH_LIMIT);
            pstmt.setInt(1, from);
            pstmt.setInt(2, size);
            pstmt.executeQuery();
            resultSet = pstmt.getResultSet();
            UserDao userDao = DaoFactoryImpl.getInstance().getUserDao();
            while (resultSet.next()) {
                orderList.add(buildOrder(con, userDao, resultSet));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
        return orderList;
    }

    @Override
    public List<Order> findAllIncomes(Connection con, int from, int size) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        List<Order> orderList = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(SELECT_ALL_INCOMES_WITH_LIMIT);
            pstmt.setInt(1, from);
            pstmt.setInt(2, size);
            pstmt.executeQuery();
            resultSet = pstmt.getResultSet();
            UserDao userDao = DaoFactoryImpl.getInstance().getUserDao();
            while (resultSet.next()) {
                orderList.add(buildOrder(con, userDao, resultSet));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
        return orderList;    }

    @Override
    public Order createIncome(Connection con, Order currentOrder) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = con.prepareStatement(CREATE_INCOME, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, currentOrder.getUser().getId());
            pstmt.setTimestamp(2, new Timestamp(currentOrder.getDate().getTime()));
            pstmt.setDouble(3, currentOrder.getTotalAmount());
            pstmt.executeUpdate();
            resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                currentOrder.setId(resultSet.getLong(1));
                return currentOrder;
            } else {
                return null;
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
    }

    @Override
    public int findRowsTotal(Connection con, String direction) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = con.prepareStatement(COUNT_ROWS_IN_ORDER);
            pstmt.setString(1, direction);
            pstmt.execute();
            resultSet = pstmt.getResultSet();
            resultSet.next();
            return resultSet.getInt("rows_total");
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
    }

    private Order buildOrder(Connection con, UserDao userDao, ResultSet resultSet) throws SQLException{
        Order order = new Order();
        order.setId(resultSet.getLong(1));
        order.setUser(userDao.read(con, resultSet.getLong(2)));
        order.setDate(resultSet.getTimestamp(3));
        order.setTotalAmount(resultSet.getDouble(4));
        return order;
    }

    @Override
    public boolean delete(Connection con, Long id) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(DELETE_ORDER)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            return true;
        }
    }

    @Override
    public void updateTotal(Connection con, long id) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(UPDATE_TOTAL_AMOUNT_BY_ID)) {
            pstmt.setLong(1, id);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Connection con, Order entity){
    }

    @Override
    public Order findByName(Connection con, String name){
        return null;
    }
}
