package com.myProject.dao.mysql;

import com.myProject.dao.OrderDetailsDao;
import com.myProject.dao.entitie.OrderDetails;
import com.myProject.exception.DaoException;

import java.sql.Connection;
import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {
    @Override
    public OrderDetails read(Connection con, Long id) throws DaoException {
        return null;
    }

    @Override
    public OrderDetails create(Connection con, OrderDetails entity) throws DaoException {
        return null;
    }

    @Override
    public void update(Connection con, OrderDetails entity) throws DaoException {

    }

    @Override
    public boolean delete(Connection con, Long id) throws DaoException {
        return false;
    }

    @Override
    public List<OrderDetails> findAll(Connection con) throws DaoException {
        return null;
    }

    @Override
    public OrderDetails findByName(Connection con, String name) throws DaoException {
        return null;
    }
}
