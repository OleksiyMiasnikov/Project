package com.myProject.service;

import com.myProject.dao.RoleDao;
import com.myProject.dao.UserDao;
import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import com.myProject.service.exception.DaoException;
import com.myProject.util.ConnectionPool;
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

    private UserManager(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    public static synchronized UserManager getInstance(UserDao userDao, RoleDao roleDao) {
        if (instance == null) {
            instance = new UserManager(userDao, roleDao);
            logger.info("Instance of UserManager created");
        }
        return instance;
    }

    public User findUser(String login) throws DaoException {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return userDao.findByName(con, login);
        } catch (SQLException e) {
            throw new DaoException("Cannot find user " + login, e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public List<User> findAllUsers(int from, int size) throws DaoException {
        logger.info("Start finding all users");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return userDao.findAll(con, from, size);
        } catch (SQLException e) {
            throw new DaoException("Cannot find all users", e);
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
        } catch (SQLException e) {
            throw new DaoException("Cannot read user by id: " + id, e);
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
        } catch (SQLException e) {
            throw new DaoException("Unable to create user: " +
                    newUser.getLogin() +
                    " with email: " +
                    newUser.getEmail(), e);
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
        } catch (SQLException e) {
            throw new DaoException("Cannot delete user " + userLogin, e);
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
        } catch (SQLException e) {
            throw new DaoException("Cannot update user " + user.getLogin(), e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public List<Role> findAllRoles(int from, int size) throws DaoException {
        logger.info("Start finding all roles");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return roleDao.findAll(con, from, size);
        } catch (SQLException e) {
            throw new DaoException("Cannot find all roles", e);
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
            return roleDao.findByName(con, role).getId();
        } catch (SQLException e) {
            throw new DaoException("Cannot get id role", e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public void deleteAll(String[] users) throws DaoException {
        logger.info("Start deleting uses");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            con.setAutoCommit(false);
            for (String user : users) {
                userDao.delete(con, Long.parseLong(user));
            }
            con.commit();
        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                logger.error("Connection is null.  " + ex);
            }
            logger.error("Unable to delete users. Rollback.");
            throw new DaoException("Unable to delete users. Rollback.", e);
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }

    public int findRowsTotal() throws DaoException {
        logger.info("Start calculation quantity of rows in table 'user'");
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            return userDao.findRowsTotal(con);
        } catch (SQLException e) {
            throw new DaoException("Unable to determine quantity of rows in table 'user'", e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }
    }
}
