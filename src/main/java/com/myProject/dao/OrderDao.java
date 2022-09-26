package com.myProject.dao;

import com.myProject.dao.entitie.Order;
import com.myProject.service.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao extends Dao<Long, Order> {
    void updateTotal(Connection con, long id) throws SQLException;

    List<Order> findAllIncomes(Connection con) throws SQLException;

    Order createIncome(Connection con, Order currentOrder) throws SQLException;
}
