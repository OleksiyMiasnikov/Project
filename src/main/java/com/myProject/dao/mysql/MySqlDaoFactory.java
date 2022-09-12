package com.myProject.dao.mysql;

import com.myProject.dao.DaoFactory;
import com.myProject.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class MySqlDaoFactory extends DaoFactory {

    private static final Logger logger = (Logger) LogManager.getLogger(MySqlDaoFactory.class);

    private UserDao instance = new MySqlUserDao();

    @Override
    public UserDao getUserDao() {
        return instance;
    }
}
