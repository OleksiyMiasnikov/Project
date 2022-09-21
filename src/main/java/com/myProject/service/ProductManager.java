package com.myProject.service;

import com.myProject.dao.ProductDao;
import com.myProject.dao.entitie.Product;
import com.myProject.exception.DaoException;
import com.myProject.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class ProductManager {
    private static final Logger logger = (Logger) LogManager.getLogger(ProductManager.class);
    private static ProductManager instance;
    private final ProductDao productDao;

    public static ProductManager getInstance(ProductDao productDao) {
        if (instance == null) {
            instance = new ProductManager(productDao);
            logger.info("Instance of ProductManager created");
        }
        return instance;
    }
    private ProductManager(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findAllProducts() throws DaoException {
        logger.info("Start finding all products");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return productDao.findAll(con);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public Product create(Product newProduct) throws DaoException{
        logger.info("Start creating new product: " + newProduct);
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return productDao.create(con, newProduct);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public void update(Product product) throws DaoException{
        logger.info("Start updating product");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            productDao.update(con, product);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }
    public Product read(long productId) throws DaoException {
        logger.info("Start reading product by id: " + productId);
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return productDao.read(con, productId);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }
}
