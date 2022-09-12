package com.myProject.dao;

import com.myProject.dao.mysql.MySqlDaoFactory;

public abstract class DaoFactory {
    private final static DaoFactory instance = new MySqlDaoFactory();
    public static DaoFactory getInstance() {
        return instance;
    }

    public abstract UserDao getUserDao();
}
