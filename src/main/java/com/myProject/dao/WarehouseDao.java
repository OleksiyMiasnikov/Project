package com.myProject.dao;

import com.myProject.entitie.Product;
import com.myProject.entitie.Warehouse;

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

    int findRowsTotal(Connection con) throws SQLException;

    Warehouse readByProduct(Connection con, Product product) throws SQLException;
}
