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

import static com.myProject.util.Constants.*;

public class GoodsDaoImpl implements GoodsDao {
    private static final Logger logger = (Logger) LogManager.getLogger(GoodsDaoImpl.class);

    @Override
    public Goods read(Connection con, Long id) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(READ_GOODS_BY_ID)) {
            pstmt.setLong(1, id);
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();
            if (resultSet.next()) {
                return buildGoods(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Unable to find goods! " + e);
            throw new DaoException("Unable to find goods! ", e);
        }
    }

    @Override
    public Goods create(Connection con, Goods entity) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(INSERT_GOODS, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getUnit());
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
    public void update(Connection con, Goods entity) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(UPDATE_GOODS, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getUnit());
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
    public List<Goods> findAll(Connection con) throws DaoException {
        try (Statement stmt = con.createStatement()) {
            stmt.executeQuery(SELECT_ALL_GOODS);
            ResultSet resultSet = stmt.getResultSet();
            List<Goods> goodsList = new ArrayList<>();
            while (resultSet.next()) {
                goodsList.add(buildGoods(resultSet));
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

    private Goods buildGoods(ResultSet resultSet) throws SQLException {
        Goods goods = new Goods();
        goods.setId(resultSet.getLong(1));
        goods.setName(resultSet.getString(2));
        goods.setPrice(resultSet.getDouble(3));
        goods.setUnit(resultSet.getString(4));
        return goods;
    }
}
