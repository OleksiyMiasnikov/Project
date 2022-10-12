package com.myProject.dao.mysql;

import com.myProject.dao.ProductDao;
import com.myProject.entitie.Product;
import com.myProject.entitie.Unit;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.myProject.util.Constants.*;

public class ProductDaoImpl implements ProductDao {
    @Override
    public Product read(Connection con, Long id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = con.prepareStatement(READ_PRODUCT_BY_ID);
            pstmt.setLong(1, id);
            pstmt.executeQuery();
            resultSet = pstmt.getResultSet();
            if (resultSet.next()) {
                return buildProduct(resultSet);
            }
            return null;
        }  finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    @Override
    public Product create(Connection con, Product entity) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = con.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getUnit().toString());
            pstmt.setDouble(3, entity.getPrice());
            pstmt.executeUpdate();
            resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
                return entity;
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
    public void update(Connection con, Product entity) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(UPDATE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getUnit().toString());
            pstmt.setDouble(3, entity.getPrice());
            pstmt.setLong(4, entity.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Product> findAll(Connection con, int from, int size) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(SELECT_ALL_PRODUCT_WITH_LIMIT);
            pstmt.setInt(1, from);
            pstmt.setInt(2, size);
            pstmt.executeQuery();
            resultSet = pstmt.getResultSet();
            while (resultSet.next()) {
                productList.add(buildProduct(resultSet));
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return productList;
    }

    private Product buildProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong(1));
        product.setName(resultSet.getString(2));
        product.setPrice(resultSet.getDouble(3));
        String unit = resultSet.getString(4);
        product.setUnit(Unit.valueOf(unit));
        return product;
    }

    @Override
    public int findRowsTotal(Connection con) throws SQLException {
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = con.createStatement();
            stmt.executeQuery(COUNT_ROWS_IN_PRODUCT);
            resultSet = stmt.getResultSet();
            resultSet.next();
            return resultSet.getInt("rows_total");
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    @Override
    public Map<String, Double> Totals(Connection con) throws SQLException {
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = con.createStatement();
            stmt.executeQuery(TOTALS_WAREHOUSE);
            resultSet = stmt.getResultSet();
            resultSet.next();
            Map<String, Double> result = new HashMap<>();
            result.put("total_quantity", resultSet.getDouble("total_quantity"));
            result.put("total_amount", resultSet.getDouble("total_amount"));
            return result;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    @Override
    public boolean delete(Connection con, Long id){
        return false;
    }
}
