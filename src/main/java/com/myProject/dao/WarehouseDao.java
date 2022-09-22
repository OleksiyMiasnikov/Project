package com.myProject.dao;

import com.myProject.dao.entitie.OrderDetails;
import com.myProject.dao.entitie.Warehouse;
import com.myProject.exception.DaoException;

import java.sql.Connection;

public interface WarehouseDao extends Dao<Long, Warehouse> {

    boolean takeOffProduct(Connection con, OrderDetails orderDetails) throws DaoException;

    void recoveryAfterDeletingOrder(Connection con, long id) throws DaoException;
}
