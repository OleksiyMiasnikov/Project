package com.myProject.service;

import com.myProject.dao.RoleDao;
import com.myProject.dao.entitie.Role;
import com.myProject.util.ConnectionPool;
import com.myProject.dao.UserDao;
import com.myProject.exception.DaoException;
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
    private final RoleDao roleDao;

    public static synchronized UserManager getInstance(UserDao userDao, RoleDao roleDao) {
        if (instance == null) {
            instance = new UserManager(userDao, roleDao);
            logger.info("Instance of UserManager created");
        }
        return instance;
    }
    private UserManager(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    public User findUser(String login) throws DaoException {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return userDao.findByName(con, login);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public List<User> findAllUsers() throws DaoException {
        logger.info("Start finding all users");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return userDao.findAll(con);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public User read(long id) throws DaoException {
        logger.info("Start read user");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return userDao.read(con, id);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public User addUser(User newUser) throws DaoException {
        logger.info("Start adding user " + newUser.getLogin());
        logger.info(newUser);
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return userDao.create(con, newUser);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public boolean deleteUser(String userLogin) throws DaoException {
        logger.info("Start deleting user " + userLogin);
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            User user = userDao.findByName(con, userLogin);
            return userDao.delete(con, user.getId());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public void updateUser(User user) throws DaoException {
        logger.info("Start updating user " + user.getLogin() + ", id: " + user.getId());
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            userDao.update(con, user);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public List<Role> findAllRoles() throws DaoException {
        logger.info("Start finding all roles");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            List<Role> rolesList = roleDao.findAll(con);
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

    public Long getIdRole(String role) throws DaoException {
        logger.info("Start getting id of " + role);
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            long id = roleDao.findByName(con, role).getId();
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

}
