package com.myProject.service;

import com.myProject.dao.mysql.MySqlConnectionPool;
import com.myProject.dao.UserDao;
import com.myProject.exception.DbException;
import com.myProject.dao.entitie.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.Connection;
import java.sql.SQLException;


public class UserManager {
    private static final Logger logger = (Logger) LogManager.getLogger(UserManager.class);
    private User currentUser;
    private static UserManager instance;
    private final UserDao userDao;

    public static UserManager getInstance(UserDao userDao) {
        if (instance == null) {
            instance = new UserManager(userDao);
            logger.info("Instance of UserManager created");
        }
        return instance;
    }
    private UserManager(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User findUser(String login, String password) throws DbException {
        Connection con = null;
        try {
            con = MySqlConnectionPool.getInstance().getConnection();
            User user = userDao.findUser(con, login);
            if (user != null && password.equals(user.getPassword())) {
                UserManager.getInstance(userDao).currentUser = user;
                return currentUser;
            }
            return null;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
                throw new DbException("Can not close connection!", e);
            }
        }
    }
}
