package com.myProject.dao.mysql;

import com.myProject.dao.ProductDao;
import com.myProject.dao.entitie.Product;
import com.myProject.dao.entitie.Unit;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myProject.util.Constants.*;

public class ProductDaoImpl implements ProductDao {
    private static final Logger logger = (Logger) LogManager.getLogger(ProductDaoImpl.class);

    @Override
    public Product read(Connection con, Long id) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(READ_PRODUCT_BY_ID)) {
            pstmt.setLong(1, id);
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();
            if (resultSet.next()) {
                return buildProduct(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Unable to find product! " + e);
            throw new DaoException("Unable to find product! ", e);
        }
    }

    @Override
    public Product create(Connection con, Product entity) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getUnit().getLabelUa());
            pstmt.setDouble(3, entity.getPrice());
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
                return entity;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Connection con, Product entity) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(UPDATE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getUnit().getLabelUa());
            pstmt.setDouble(3, entity.getPrice());
            pstmt.setLong(4, entity.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Connection con, Long id) throws DaoException {
        return false;
    }

    @Override
    public List<Product> findAll(Connection con) throws DaoException {
        try (Statement stmt = con.createStatement()) {
            stmt.executeQuery(SELECT_ALL_PRODUCT);
            ResultSet resultSet = stmt.getResultSet();
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                productList.add(buildProduct(resultSet));
            }
            Collections.sort(productList);
            resultSet.close();
            return productList;
        } catch (SQLException e) {
            logger.error("Unable to find all products! " + e);
            throw new DaoException("Unable to find all products! ", e);
        }
    }

    @Override
    public Product findByName(Connection con, String name) throws DaoException {
        return null;
    }

    private Product buildProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong(1));
        product.setName(resultSet.getString(2));
        product.setPrice(resultSet.getDouble(3));
        product.setUnit(Unit.valueOfLabel(resultSet.getString(4)));
        return product;
    }
}
