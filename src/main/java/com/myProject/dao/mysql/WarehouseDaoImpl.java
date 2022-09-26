package com.myProject.dao.mysql;

import com.myProject.dao.ProductDao;
import com.myProject.dao.WarehouseDao;
import com.myProject.dao.entitie.Warehouse;
import com.myProject.service.exception.DaoException;
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
    public void updateQuantity(Connection con, double quantity, long id) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(UPDATE_QUANTITY)) {
            pstmt.setDouble(1, quantity);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Unable to update quantity in warehouse " + e);
            throw new DaoException("Unable to update quantity in warehouse " + e);
        }
    }

    @Override
    public void recoveryAfterDeletingOrder(Connection con, long id) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(RECOVERY_QUANTITY_AFTER_DELETING_ORDER)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Unable to recovery quantity in warehouse " + e);
            throw new DaoException("Unable to recovery quantity in warehouse " + e);
        }
    }
}
