package com.myProject.dao.mysql;

import com.myProject.dao.GoodsDao;
import com.myProject.dao.WarehouseDao;
import com.myProject.dao.entitie.Warehouse;
import com.myProject.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myProject.dao.Constants.*;


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
            GoodsDao goodsDao = DaoFactoryImpl.getInstance().getGoodsDao();
            while (resultSet.next()) {
                warehouseList.add(buildWarehouse(con, goodsDao, resultSet));
            }
            Collections.sort(warehouseList);
            resultSet.close();
            return warehouseList;
        } catch (SQLException e) {
            logger.error("Unable to find all users! " + e);
            throw new DaoException("Unable to find all users! ", e);
        }
    }

    private Warehouse buildWarehouse(Connection con, GoodsDao goodsDao, ResultSet resultSet) throws SQLException {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(resultSet.getLong(1));
        warehouse.setQuantity(resultSet.getInt(2));
        warehouse.setGoods(goodsDao.read(con, resultSet.getLong(3)));
        return warehouse;
    }

    @Override
    public Warehouse findByName(Connection con, String name) throws DaoException {
        return null;
    }
}
