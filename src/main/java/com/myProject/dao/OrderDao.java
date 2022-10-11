package com.myProject.dao;

import com.myProject.dto.ReportItem;
import com.myProject.entitie.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderDao extends Dao<Long, Order> {
    void updateTotal(Connection con, long id) throws SQLException;

    List<Order> findAll(Connection con, String direction, int from, int size) throws SQLException;

    Order create(Connection con, Order entity, String direction) throws SQLException;

    int findRowsTotal(Connection con, String direction) throws SQLException;

    Map<String, Double> Totals(Connection con, String direction) throws SQLException;

    List<ReportItem> createReport(Connection con) throws SQLException;

    Date[] determineDates(Connection con) throws SQLException;

    void closeReportedOrders(Connection con) throws SQLException;
}
