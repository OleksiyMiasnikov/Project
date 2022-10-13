package com.myProject.dao.mysql;

import com.myProject.dao.ProductDao;
import com.myProject.dao.WarehouseDao;
import com.myProject.entitie.Product;
import com.myProject.entitie.Warehouse;
import com.myProject.service.exception.DaoException;

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
        return Warehouse.builder()
                .id(resultSet.getLong(1))
                .quantity(resultSet.getDouble(2))
                .product(productDao.read(con, resultSet.getLong(3)))
                .build();
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
    public void recoveryAfterDeletingOrder(Connection con, long id, String direction) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(RECOVERY_QUANTITY_AFTER_DELETING_ORDER)) {
            int sign = 1;
            if ("IN".equals(direction)) {
                sign = -1;
            }
            pstmt.setInt(1, sign);
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
            stmt.executeQuery(COUNT_ROWS_IN_WAREHOUSE);
            resultSet = stmt.getResultSet();
            resultSet.next();
            return resultSet.getInt("rows_total");
        } finally {
            if (resultSet != null) resultSet.close();
            if (stmt != null) stmt.close();
        }
    }

    @Override
    public Warehouse readByProduct(Connection con, Product product) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = con.prepareStatement(FIND_IN_WAREHOUSE_BY_PRODUCT_ID);
            pstmt.setLong(1, product.getId());
            pstmt.executeQuery();
            resultSet = pstmt.getResultSet();
            if (resultSet.next()) {
                return Warehouse.builder()
                        .id(resultSet.getLong(1))
                        .quantity(resultSet.getDouble(2))
                        .product(product)
                        .build();
            }
            return null;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }

    }

    @Override
    public Warehouse read(Connection con, Long id) throws SQLException {
        return null;
    }

    @Override
    public Warehouse create(Connection con, Warehouse entity) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = con.prepareStatement(INSERT_WAREHOUSE, Statement.RETURN_GENERATED_KEYS);
            pstmt.setDouble(1, entity.getQuantity());
            pstmt.setLong(2, entity.getProduct().getId());
            pstmt.executeUpdate();
            resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
                return entity;
            }
            throw new DaoException("Insertion failed");
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    @Override
    public void update(Connection con, Warehouse entity){ }
    @Override
    public boolean delete(Connection con, Long id){
        return false;
    }
}
