package com.myProject.dao;

import com.myProject.exception.DaoException;

import java.sql.Connection;
import java.util.List;


public interface Dao<K, T> {

    T read(Connection con, K id) throws DaoException;

    T create(Connection con, T entity) throws DaoException;

    void update(Connection con, T entity) throws DaoException;

    boolean delete(Connection con, K id) throws DaoException;

    List<T> findAll(Connection con) throws DaoException;

    T findByName(Connection con, String name) throws DaoException;
}
