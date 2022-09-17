package com.myProject.dao.mysql;

import com.myProject.dao.entitie.Order;
import com.myProject.dao.OrderDao;
import com.myProject.exception.DaoException;

import java.sql.Connection;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public Order read(Connection con, Long id) throws DaoException {
        return null;
    }

    @Override
    public Order create(Connection con, Order entity) throws DaoException {
        return null;
    }

    @Override
    public void update(Connection con, Order entity) throws DaoException {

    }

    @Override
    public boolean delete(Connection con, Long id) throws DaoException {
        return false;
    }

    @Override
    public List<Order> findAll(Connection con) throws DaoException {
        return null;
    }

    @Override
    public Order findByName(Connection con, String name) throws DaoException {
        return null;
    }
}
