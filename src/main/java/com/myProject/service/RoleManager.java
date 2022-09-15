package com.myProject.service;

import com.myProject.dao.RoleDao;
import com.myProject.dao.entitie.Role;
import com.myProject.exception.DaoException;
import com.myProject.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class RoleManager {
    private static final Logger logger = (Logger) LogManager.getLogger(RoleManager.class);
    private static RoleManager instance;
    private final RoleDao roleDao;

    public static RoleManager getInstance(RoleDao roleDao) {
        if (instance == null) {
            instance = new RoleManager(roleDao);
            logger.info("Instance of RoleDao created");
        }
        return instance;
    }
    private RoleManager(RoleDao roleDao) {
        this.roleDao = roleDao;
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
