package com.myProject.dao;

import com.myProject.dao.mysql.MySqlDaoFactory;
import com.myProject.dao.mysql.MySqlUserDao;

public abstract class DaoFactory {
    private static DaoFactory instance = new MySqlDaoFactory();
    public static DaoFactory getInstance() {
        return instance;
    }

    public abstract UserDao getUserDao();
}
