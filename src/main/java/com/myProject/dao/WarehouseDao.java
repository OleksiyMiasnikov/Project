package com.myProject.dao;

import com.myProject.dao.Dao;
import com.myProject.dao.entitie.OrderDetails;
import com.myProject.dao.entitie.Warehouse;
import com.myProject.exception.DaoException;

import java.sql.Connection;

public interface WarehouseDao extends Dao<Long, Warehouse> {

    boolean offTakeProduct(Connection con, OrderDetails orderDetails) throws DaoException;
}
