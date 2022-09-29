package com.myProject.dao.mysql;

import com.myProject.dao.ProductDao;
import com.myProject.dao.WarehouseDao;
import com.myProject.dao.entitie.Warehouse;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myProject.util.Constants.*;


public class WarehouseDaoImpl implements WarehouseDao {

    @Override
    public List<Warehouse> findAll(Connection con, int from, int size) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        List<Warehouse> warehouseList = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(SELECT_ALL_IN_WAREHOUSE_WITH_LIMIT);
            pstmt.setInt(1, from);
            pstmt.setInt(2, size);
            pstmt.executeQuery();
            resultSet = pstmt.getResultSet();
            ProductDao productDao = DaoFactoryImpl.getInstance().getProductDao();
            while (resultSet.next()) {
                warehouseList.add(buildWarehouse(con, productDao, resultSet));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
        Collections.sort(warehouseList);
        return warehouseList;
    }

    /**
     * create instance of class Warehouse with data from resultSet
     * @param con
     * @param productDao
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private Warehouse buildWarehouse(Connection con, ProductDao productDao, ResultSet resultSet) throws SQLException {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(resultSet.getLong(1));
        warehouse.setQuantity(resultSet.getInt(2));
        warehouse.setProduct(productDao.read(con, resultSet.getLong(3)));
        return warehouse;
    }

    @Override
    public void updateQuantity(Connection con, double quantity, long id) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(UPDATE_QUANTITY)) {
            pstmt.setDouble(1, quantity);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public int findRowsTotal(Connection con) throws SQLException {
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = con.createStatement();
            stmt.executeQuery(COUNT_ROWS_IN_WAREEHOUSE);
            resultSet = stmt.getResultSet();
            resultSet.next();
            return resultSet.getInt("rows_total");
        } finally {
            if (resultSet != null) resultSet.close();
            if (stmt != null) stmt.close();
        }
    }

    @Override
    public void recoveryAfterDeletingOrder(Connection con, long id) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(RECOVERY_QUANTITY_AFTER_DELETING_ORDER)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Warehouse read(Connection con, Long id){
        return null;
    }

    @Override
    public Warehouse create(Connection con, Warehouse entity){
        return null;
    }

    @Override
    public Warehouse findByName(Connection con, String name){
        return null;
    }

    @Override
    public void update(Connection con, Warehouse entity){

    }

    @Override
    public boolean delete(Connection con, Long id){
        return false;
    }
}
