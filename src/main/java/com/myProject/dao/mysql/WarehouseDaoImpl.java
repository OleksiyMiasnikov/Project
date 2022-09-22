package com.myProject.dao.mysql;

import com.myProject.dao.ProductDao;
import com.myProject.dao.WarehouseDao;
import com.myProject.dao.entitie.OrderDetails;
import com.myProject.dao.entitie.Warehouse;
import com.myProject.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myProject.util.Constants.*;


public class WarehouseDaoImpl implements WarehouseDao {
    private static final Logger logger = (Logger) LogManager.getLogger(WarehouseDaoImpl.class);
    @Override
    public Warehouse read(Connection con, Long id) throws DaoException {
        return null;
    }

    @Override
    public Warehouse create(Connection con, Warehouse entity) throws DaoException {
        return null;
    }

    @Override
    public void update(Connection con, Warehouse entity) throws DaoException {

    }

    @Override
    public boolean delete(Connection con, Long id) throws DaoException {
        return false;
    }

    @Override
    public List<Warehouse> findAll(Connection con) throws DaoException {
        try (Statement stmt = con.createStatement()) {
            stmt.executeQuery(SELECT_ALL_IN_WAREHOUSE);
            ResultSet resultSet = stmt.getResultSet();
            List<Warehouse> warehouseList = new ArrayList<>();
            ProductDao productDao = DaoFactoryImpl.getInstance().getProductDao();
            while (resultSet.next()) {
                warehouseList.add(buildWarehouse(con, productDao, resultSet));
            }
            Collections.sort(warehouseList);
            resultSet.close();
            return warehouseList;
        } catch (SQLException e) {
            logger.error("Unable to find all users! " + e);
            throw new DaoException("Unable to find all users! ", e);
        }
    }

    private Warehouse buildWarehouse(Connection con, ProductDao productDao, ResultSet resultSet) throws SQLException {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(resultSet.getLong(1));
        warehouse.setQuantity(resultSet.getInt(2));
        warehouse.setProduct(productDao.read(con, resultSet.getLong(3)));
        return warehouse;
    }

    @Override
    public Warehouse findByName(Connection con, String name) throws DaoException {
        return null;
    }

    @Override
    public boolean offTakeProduct(Connection con, OrderDetails orderDetails) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(OFF_TAKE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDouble(1, orderDetails.getQuantity());
            pstmt.setLong(2, orderDetails.getProduct().getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to take off product: "+ orderDetails.getProduct().getName() + ". Error" + e);
            if (e.getErrorCode() == ERROR_CODE_OUT_OF_RANGE) {
                throw new DaoException("Unable to take off product. Out of range value for column 'quantity'" + e);
            }
            throw new DaoException("Unable to take off product: "+ orderDetails.getProduct().getName() + ". Error" + e);
        }
    }
}
