package com.myProject.dao;

import com.myProject.dao.entitie.OrderDetails;
import com.myProject.dao.entitie.Warehouse;
import com.myProject.service.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public interface WarehouseDao extends Dao<Long, Warehouse> {
    /**
     *
     * @param con
     * @param id
     * @throws SQLException
     */
    void recoveryAfterDeletingOrder(Connection con, long id) throws SQLException;

    /**
     * updates quantity in table `warehouse` in row with product_id equal id
     * @param con
     * @param quantity
     * @param id
     * @throws SQLException
     */

    void updateQuantity(Connection con, double quantity, long id) throws SQLException;
}
