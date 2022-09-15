package com.myProject.service;

import com.myProject.dao.GoodsDao;
import com.myProject.dao.entitie.Goods;
import com.myProject.exception.DaoException;
import com.myProject.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class GoodsManager {
    private static final Logger logger = (Logger) LogManager.getLogger(GoodsManager.class);
    private static GoodsManager instance;
    private final GoodsDao goodsDao;

    public static GoodsManager getInstance(GoodsDao goodsDao) {
        if (instance == null) {
            instance = new GoodsManager(goodsDao);
            logger.info("Instance of GoodsManager created");
        }
        return instance;
    }
    private GoodsManager(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    public List<Goods> findAllGoods() throws DaoException {
        logger.info("Start finding all goods");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return goodsDao.findAll(con);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public Goods create(Goods newGoods) throws DaoException{
        logger.info("Start creating new goods: " + newGoods);
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return goodsDao.create(con, newGoods);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public void update(Goods goods) throws DaoException{
        logger.info("Start creating new goods");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            goodsDao.update(con, goods);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }
}
