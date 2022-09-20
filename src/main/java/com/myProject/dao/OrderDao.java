package com.myProject.dao;

import com.myProject.dao.entitie.Order;
import com.myProject.exception.DaoException;

import java.sql.Connection;

public interface OrderDao extends Dao<Long, Order> {
    void updateTotal(Connection con, long id) throws DaoException;
}
