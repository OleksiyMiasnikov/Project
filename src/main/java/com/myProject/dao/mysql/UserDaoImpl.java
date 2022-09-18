package com.myProject.dao.mysql;

import com.myProject.dao.UserDao;
import com.myProject.dao.entitie.Role;
import com.myProject.dao.entitie.User;
import com.myProject.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myProject.util.Constants.*;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = (Logger) LogManager.getLogger(UserDaoImpl.class);

    public User findByName(Connection con, String name) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(SELECT_USER)) {
            pstmt.setString(1, name);
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();
            if (resultSet.next()) {
                return buildUser(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Cannot make search user by login! " + e);
            throw new DaoException("Cannot make search user by login! ", e);
        }
    }

    @Override
    public List<User> findAll(Connection con) throws DaoException {
        try (Statement stmt = con.createStatement()) {
            stmt.executeQuery(SELECT_ALL_USERS);
            ResultSet resultSet = stmt.getResultSet();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                userList.add(buildUser(resultSet));
            }
            Collections.sort(userList);
            resultSet.close();
            return userList;
        } catch (SQLException e) {
            logger.error("Unable to find all users! " + e);
            throw new DaoException("Unable to find all users! ", e);
        }
    }

    @Override
    public User read(Connection con, Long id) throws DaoException {
        try (PreparedStatement pstmt = con.prepareStatement(READ_USER_BY_ID)) {
            pstmt.setLong(1, id);
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();
            if (resultSet.next()) {
                return buildUser(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Unable to find user! " + e);
            throw new DaoException("Unable to find user! ", e);
        }
    }

    @Override
    public User create(Connection con, User newUser) {
        try (PreparedStatement pstmt = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, newUser.getLogin());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getEmail());
            pstmt.setLong(4, newUser.getRole().getId());
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                newUser.setId(resultSet.getLong(1));
                 return newUser;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean delete(Connection con, Long id) {
        try (PreparedStatement pstmt = con.prepareStatement(DELETE_USER)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Connection con, User user) {
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

    private User buildUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(1));
        user.setLogin(resultSet.getString(2));
        user.setPassword(resultSet.getString(3));
        user.setEmail(resultSet.getString(4));
        user.setRole(new Role(resultSet.getInt(5), resultSet.getString(6)));
        return user;
    }
}
