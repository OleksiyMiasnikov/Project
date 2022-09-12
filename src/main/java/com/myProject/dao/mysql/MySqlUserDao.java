package com.myProject.dao.mysql;

import com.myProject.dao.UserDao;
import com.myProject.dao.entitie.User;
import com.myProject.exception.DbException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.*;
import java.util.ArrayList;
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
            return userList;
        } catch (SQLException e) {
            logger.error("Cannot make search all users! " + e);
            throw new DbException("Cannot make search all users! ", e);
        }
    }
}
