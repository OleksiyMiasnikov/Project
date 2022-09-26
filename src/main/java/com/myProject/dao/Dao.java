package com.myProject.dao;

import com.myProject.service.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface Dao<K, T> {

    T read(Connection con, K id) throws SQLException;

    T create(Connection con, T entity) throws SQLException;

    void update(Connection con, T entity) throws SQLException;

    boolean delete(Connection con, K id) throws SQLException;

    List<T> findAll(Connection con) throws SQLException;

    T findByName(Connection con, String name) throws SQLException;
}
