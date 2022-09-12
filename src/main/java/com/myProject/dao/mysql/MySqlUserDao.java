package com.myProject.dao.mysql;

import com.myProject.dao.UserDao;
import com.myProject.dao.entitie.User;
import com.myProject.exception.DbException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDao implements UserDao {
    private static final Logger logger = (Logger) LogManager.getLogger(MySqlDaoFactory.class);

    private static final String SELECT_USER = "SELECT `users`.`id`, `login`, `password`, `email`, `roles_id`, `name` as role_name FROM `users` JOIN `roles` ON `users`.`roles_id` = `roles`.`id` WHERE `login`= ?";
    private static final String SELECT_ALL_USERS = "SELECT `users`.`id`, `login`, `password`, `email`, `roles_id`, `name` as role_name FROM `users` JOIN `roles` ON `users`.`roles_id` = `roles`.`id`";
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
            throw new DbException("Cannot make search all users! ", e);
        }
    }
}
