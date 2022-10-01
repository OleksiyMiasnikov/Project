package com.myProject.dao;

import com.myProject.dao.entitie.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDao extends Dao<Long, OrderDetails> {
    List<OrderDetails> readByOrderId(Connection con, int from, int size, long id) throws SQLException;

    boolean deleteByOrderId(Connection con, long id) throws SQLException;

    int findRowsTotal(Connection con, long id) throws SQLException;

}
