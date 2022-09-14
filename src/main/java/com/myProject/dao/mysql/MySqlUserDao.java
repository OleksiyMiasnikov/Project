package com.myProject.dao.mysql;

import com.myProject.dao.UserDao;
import com.myProject.dao.entitie.Role;
import com.myProject.dao.entitie.User;
import com.myProject.exception.DbException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myProject.dao.Constants.*;

public class MySqlUserDao implements UserDao {
    private static final Logger logger = (Logger) LogManager.getLogger(MySqlDaoFactory.class);

    public User findUser(Connection con, String login) throws DbException {
        try (PreparedStatement pstmt = con.prepareStatement(SELECT_USER)) {
            pstmt.setString(1, login);
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();
            if (resultSet.next()) {
                return User.newInstance(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Cannot make search user by login! " + e);
            throw new DbException("Cannot make search user by login! ", e);
        }
    }

    @Override
    public List<User> findAllUsers(Connection con) throws DbException{
        try (Statement stmt = con.createStatement()) {
            stmt.executeQuery(SELECT_ALL_USERS);
            ResultSet resultSet = stmt.getResultSet();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                userList.add(User.newInstance(resultSet));
            }
            Collections.sort(userList);
            resultSet.close();
            return userList;
        } catch (SQLException e) {
            logger.error("Unable to find all users! " + e);
            throw new DbException("Unable to find all users! ", e);
        }
    }

    @Override
    public List<Role> findAllRoles(Connection con) throws DbException {
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
            throw new DbException("Unable to find all roles!! ", e);
        }
    }

    @Override
    public boolean addUser(Connection con, User newUser) {
        try (PreparedStatement pstmt = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, newUser.getLogin());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getEmail());
            pstmt.setLong(4, newUser.getRole().getId());
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                newUser.setId(resultSet.getLong(1));
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getIdRole(Connection con, String name) throws DbException {
        try (PreparedStatement pstmt = con.prepareStatement(GET_ROLE_ID)) {
            pstmt.setString(1, name);
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                logger.error("Cannot finde role id!");
                throw new DbException("Cannot finde role id! ");
            }
        } catch (SQLException e) {
            logger.error("Cannot finde role id! " + e);
            throw new DbException("Cannot finde role id! ", e);
        }
    }

    @Override
    public boolean deleteUser(Connection con, String userLogin) {
        try (PreparedStatement pstmt = con.prepareStatement(DELETE_USER)) {
            pstmt.setString(1, userLogin);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(Connection con, User user) {
        try (PreparedStatement pstmt = con.prepareStatement(UPDATE_USER)) {
            pstmt.setString(1, user.getLogin());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setLong(4, user.getRole().getId());
            pstmt.setLong(5, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
