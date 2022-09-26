package com.myProject.dao;

import com.myProject.dao.entitie.OrderDetails;
import com.myProject.dao.entitie.Warehouse;
import com.myProject.service.exception.DaoException;

import java.sql.Connection;

public interface WarehouseDao extends Dao<Long, Warehouse> {

    void recoveryAfterDeletingOrder(Connection con, long id) throws DaoException;

    void updateQuantity(Connection con, double quantity, long id) throws DaoException;
}
