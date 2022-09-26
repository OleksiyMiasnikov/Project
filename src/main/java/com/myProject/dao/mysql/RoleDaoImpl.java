package com.myProject.dao.mysql;

import com.myProject.dao.RoleDao;
import com.myProject.dao.entitie.Role;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myProject.util.Constants.*;

public class RoleDaoImpl implements RoleDao {
    private static final Logger logger = (Logger) LogManager.getLogger(RoleDaoImpl.class);

    @Override
    public Role read(Connection con, Long id) throws DaoException {
        return null;
    }

    @Override
    public Role create(Connection con, Role entity) throws DaoException {
        return null;
    }

    @Override
    public void update(Connection con, Role entity) throws DaoException {

    }

    @Override
    public boolean delete(Connection con, Long id) throws DaoException {
        return false;
    }

    @Override
    public List<Role> findAll(Connection con) throws DaoException {
        try (Statement stmt = con.createStatement()) {
            stmt.executeQuery(SELECT_ALL_ROLES);
            ResultSet resultSet = stmt.getResultSet();
            List<Role> rolesList = new ArrayList<>();
            while (resultSet.next()) {
                rolesList.add(Role.newInstance(resultSet));
            }
            Collections.sort(rolesList);
            return rolesList;
        } catch (SQLException e) {
            logger.error("Unable to find all roles!! " + e);
            throw new DaoException("Unable to find all roles!! ", e);
        }
    }

    @Override
    public Role findByName(Connection con, String name) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(GET_ROLE_ID)) {
            pstmt.setString(1, name);
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();
            if (resultSet.next()) {
                return new Role(resultSet.getLong(1), name);
            } else {
                logger.error("Cannot find role by name!");
                throw new DaoException("Cannot find role by name! ");
            }
        } catch (SQLException e) {
            logger.error("Cannot find role by name! " + e);
            throw new DaoException("Cannot find role by name! ", e);
        }
    }

}
