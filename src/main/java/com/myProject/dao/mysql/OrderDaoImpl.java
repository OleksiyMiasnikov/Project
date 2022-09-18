package com.myProject.dao.mysql;

import com.myProject.dao.UserDao;
import com.myProject.dao.entitie.Order;
import com.myProject.dao.OrderDao;
import com.myProject.dao.entitie.User;
import com.myProject.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myProject.dao.Constants.*;

public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = (Logger) LogManager.getLogger(OrderDaoImpl.class);
    @Override
    public Order read(Connection con, Long id) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(READ_ORDER_BY_ID)) {
            pstmt.setLong(1, id);
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();
            UserDao userDao = DaoFactoryImpl.getInstance().getUserDao();
            if (resultSet.next()) {
                return buildOrder(con, userDao, resultSet);
            } else {
                return null;
            }
        } catch (SQLException | ParseException e) {
            logger.error("Unable to find goods! " + e);
            throw new DaoException("Unable to find goods! ", e);
        }
    }

    @Override
    public Order create(Connection con, Order entity) throws DaoException {
        return null;
    }

    @Override
    public void update(Connection con, Order entity) throws DaoException {

    }

    @Override
    public boolean delete(Connection con, Long id) throws DaoException {
        return false;
    }

    @Override
    public List<Order> findAll(Connection con) throws DaoException {
        try (Statement stmt = con.createStatement()) {
            stmt.executeQuery(SELECT_ALL_ORDERS);
            ResultSet resultSet = stmt.getResultSet();
            List<Order> orderList = new ArrayList<>();
            UserDao userDao = DaoFactoryImpl.getInstance().getUserDao();
            while (resultSet.next()) {
                orderList.add(buildOrder(con, userDao, resultSet));
            }
            Collections.sort(orderList);
            resultSet.close();
            return orderList;
        } catch (SQLException | ParseException e) {
            logger.error("Unable to find all orders! " + e);
            throw new DaoException("Unable to find all orders! ", e);
        }
    }

    private Order buildOrder(Connection con, UserDao userDao, ResultSet resultSet) throws SQLException, ParseException {
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Order order = new Order();
        order.setId(resultSet.getLong(1));
        order.setUser(userDao.read(con, resultSet.getLong(2)));
        order.setDate(date.parse(resultSet.getString(3)));
        order.setAmount(resultSet.getDouble(4));
        return order;
    }

    @Override
    public Order findByName(Connection con, String name) throws DaoException {
        return null;
    }
}
