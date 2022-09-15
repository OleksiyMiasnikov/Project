package com.myProject.dao.mysql;

import com.myProject.dao.GoodsDao;
import com.myProject.dao.entitie.Goods;
import com.myProject.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myProject.dao.Constants.*;

public class GoodsDaoImpl implements GoodsDao {
    private static final Logger logger = (Logger) LogManager.getLogger(GoodsDaoImpl.class);

    @Override
    public Goods read(Connection con, Long id) throws DaoException {
        return null;
    }

    @Override
    public Goods create(Connection con, Goods entity) throws DaoException {
        return null;
    }

    @Override
    public void update(Connection con, Goods entity) throws DaoException {

    }

    @Override
    public boolean delete(Connection con, Long id) throws DaoException {
        return false;
    }

    @Override
    public List<Goods> findAll(Connection con) throws DaoException {
        try (Statement stmt = con.createStatement()) {
            stmt.executeQuery(SELECT_ALL_GOODS);
            ResultSet resultSet = stmt.getResultSet();
            List<Goods> goodsList = new ArrayList<>();
            while (resultSet.next()) {
                goodsList.add(Goods.newInstance(resultSet));
            }
            Collections.sort(goodsList);
            resultSet.close();
            return goodsList;
        } catch (SQLException e) {
            logger.error("Unable to find all goods! " + e);
            throw new DaoException("Unable to find all goods! ", e);
        }
    }

    @Override
    public Goods findByName(Connection con, String name) throws DaoException {
        return null;
    }
}
