package com.myProject.dao;

import com.myProject.dao.entitie.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao extends Dao<Long, Order> {
    void updateTotal(Connection con, long id) throws SQLException;

    List<Order> findAllIncomes(Connection con, int from, int size) throws SQLException;

    Order createIncome(Connection con, Order currentOrder) throws SQLException;

    int findRowsTotal(Connection con, String direction) throws SQLException;
}
