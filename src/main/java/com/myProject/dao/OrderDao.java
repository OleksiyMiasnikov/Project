package com.myProject.dao;

import com.myProject.dao.entitie.Order;
import com.myProject.service.exception.DaoException;

import java.sql.Connection;
import java.util.List;

public interface OrderDao extends Dao<Long, Order> {
    void updateTotal(Connection con, long id) throws DaoException;

    List<Order> findAllIncomes(Connection con) throws DaoException;

    Order createIncome(Connection con, Order currentOrder) throws DaoException;
}
