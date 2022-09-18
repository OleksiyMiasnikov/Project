package com.myProject.dao.mysql;

import com.myProject.dao.GoodsDao;
import com.myProject.dao.OrderDetailsDao;
import com.myProject.dao.entitie.Order;
import com.myProject.dao.entitie.OrderDetails;
import com.myProject.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myProject.dao.Constants.READ_DETAILS_BY_ORDER_ID;


public class OrderDetailsDaoImpl implements OrderDetailsDao {
    private static final Logger logger = (Logger) LogManager.getLogger(OrderDetailsDaoImpl.class);
    @Override
    public OrderDetails read(Connection con, Long id) throws DaoException {
        return null;
    }

    @Override
    public OrderDetails create(Connection con, OrderDetails entity) throws DaoException {
        return null;
    }

    @Override
    public void update(Connection con, OrderDetails entity) throws DaoException {

    }

    @Override
    public boolean delete(Connection con, Long id) throws DaoException {
        return false;
    }

    @Override
    public List<OrderDetails> findAll(Connection con) throws DaoException {
        return null;
    }

    @Override
    public OrderDetails findByName(Connection con, String name) throws DaoException {
        return null;
    }

    @Override
    public List<OrderDetails> detailsByOrderId(Connection con, long id) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(READ_DETAILS_BY_ORDER_ID)) {
            pstmt.setLong(1, id);
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();
            List<OrderDetails> orderDetailsList = new ArrayList<>();
            GoodsDao goodsDao = DaoFactoryImpl.getInstance().getGoodsDao();
            Order order = DaoFactoryImpl.getInstance().getOrderDao().read(con, id);
            while (resultSet.next()) {
                orderDetailsList.add(buildOrderDetails(con, goodsDao, order, resultSet));
            }
            Collections.sort(orderDetailsList);
            resultSet.close();
            return orderDetailsList;
        } catch (SQLException e) {
            logger.error("Unable to find order details! " + e);
            throw new DaoException("Unable to find order details! ", e);
        }
    }

    private OrderDetails buildOrderDetails(Connection con, GoodsDao goodsDao, Order order, ResultSet resultSet) throws SQLException {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(resultSet.getLong(1));
        orderDetails.setOrder(order);
        orderDetails.setGoods(goodsDao.read(con, resultSet.getLong(3)));
        orderDetails.setQuantity(resultSet.getInt(4));
        orderDetails.setPrice(resultSet.getDouble(5));
        return orderDetails;
    }
}
