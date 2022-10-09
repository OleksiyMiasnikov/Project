package com.myProject.dao;

import com.myProject.entitie.Order;
import com.myProject.entitie.OrderDetails;
import com.myProject.entitie.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDao extends Dao<Long, OrderDetails> {
    List<OrderDetails> readByOrderId(Connection con, int from, int size, long id) throws SQLException;

    boolean deleteByOrderId(Connection con, long id) throws SQLException;

    int findRowsTotal(Connection con, long id) throws SQLException;

    OrderDetails readByOrderAndProduct(Connection con, Order order, Product product) throws SQLException;

    void updateQuantityInOrderDetail(Connection con, long id, double quantity) throws SQLException;
}
