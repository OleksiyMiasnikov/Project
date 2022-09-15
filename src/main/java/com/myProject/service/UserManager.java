package com.myProject.service;

import com.myProject.dao.entitie.Goods;
import com.myProject.dao.entitie.Role;
import com.myProject.dao.mysql.MySqlConnectionPool;
import com.myProject.dao.UserDao;
import com.myProject.exception.DbException;
import com.myProject.dao.entitie.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class UserManager {
    private static final Logger logger = (Logger) LogManager.getLogger(UserManager.class);
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

    public User findUser(String login) throws DbException {
        Connection con = null;
        try {
            con = MySqlConnectionPool.getInstance().getConnection();
            return userDao.findUser(con, login);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public List<User> findAllUsers() throws DbException{
        logger.info("Start finding all users");
        Connection con = null;
        try {
            con = MySqlConnectionPool.getInstance().getConnection();
            return userDao.findAllUsers(con);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }


    public List<Role> findAllRoles() throws DbException {
        logger.info("Start finding all roles");
        Connection con = null;
        try {
            con = MySqlConnectionPool.getInstance().getConnection();
            List<Role> rolesList = userDao.findAllRoles(con);
            logger.info("Finish finding all roles");
            return rolesList;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public boolean addUser(User newUser) throws DbException {
        logger.info("Start adding user " + newUser.getLogin());
        logger.info(newUser);
        Connection con = null;
        try {
            con = MySqlConnectionPool.getInstance().getConnection();
            return userDao.addUser(con, newUser);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public User deleteUser(String userLogin) throws DbException {
        logger.info("Start deleting user " + userLogin);
        Connection con = null;
        try {
            con = MySqlConnectionPool.getInstance().getConnection();
            User user = userDao.findUser(con, userLogin);
            if (userDao.deleteUser(con, userLogin)) {
                return user;
            } else {
                return null;
            }
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public void updateUser(User user) throws DbException {
        logger.info("Start updating user " + user.getLogin() + ", id: " + user.getId());
        Connection con = null;
        try {
            con = MySqlConnectionPool.getInstance().getConnection();
            userDao.updateUser(con, user);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }
    public Long getIdRole(String role) throws DbException {
        logger.info("Start getting id of " + role);
        Connection con = null;
        try {
            con = MySqlConnectionPool.getInstance().getConnection();
            long id = userDao.getIdRole(con, role);
            logger.info(role + " has id: " + id);
            return id;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public List<Goods> findAllGoods() throws DbException {
        logger.info("Start finding all goods");
        Connection con = null;
        try {
            con = MySqlConnectionPool.getInstance().getConnection();
            return userDao.findAllGoods(con);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }
}
