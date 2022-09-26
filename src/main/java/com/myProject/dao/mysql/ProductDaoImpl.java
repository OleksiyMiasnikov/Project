package com.myProject.dao.mysql;

import com.myProject.dao.ProductDao;
import com.myProject.dao.entitie.Product;
import com.myProject.dao.entitie.Unit;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            } else {
                return null;
            }
        }  finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
    }

    @Override
    public Product create(Connection con, Product entity) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = con.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getUnit().getLabelUa());
            pstmt.setDouble(3, entity.getPrice());
            pstmt.executeUpdate();
            resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
                return entity;
            } else {
                return null;
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
    }

    @Override
    public void update(Connection con, Product entity) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(UPDATE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getUnit().getLabelUa());
            pstmt.setDouble(3, entity.getPrice());
            pstmt.setLong(4, entity.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Product> findAll(Connection con) throws SQLException {
        Statement stmt = null;
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<>();
        try {
            stmt = con.createStatement();
            stmt.executeQuery(SELECT_ALL_PRODUCT);
            resultSet = stmt.getResultSet();
            while (resultSet.next()) {
                productList.add(buildProduct(resultSet));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (stmt != null) stmt.close();
        }
        Collections.sort(productList);
        return productList;
    }

    private Product buildProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong(1));
        product.setName(resultSet.getString(2));
        product.setPrice(resultSet.getDouble(3));
        product.setUnit(Unit.valueOfLabel(resultSet.getString(4)));
        return product;
    }

    @Override
    public Product findByName(Connection con, String name){
        return null;
    }

    @Override
    public boolean delete(Connection con, Long id){
        return false;
    }
}
