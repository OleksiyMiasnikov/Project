package com.myProject.dao.mysql;

import com.myProject.dao.DaoFactory;
import com.myProject.dao.UserDao;

public class MySqlDaoFactory extends DaoFactory {

    private final UserDao instance = new MySqlUserDao();

    @Override
    public UserDao getUserDao() {
        return instance;
    }
}
