package com.myProject.dao;

import com.myProject.dao.entitie.User;
import com.myProject.exception.DbException;

import java.sql.Connection;
import java.util.List;

public interface UserDao {
    User findUser(Connection con, String login) throws DbException;

    List<User> findAllUsers(Connection con) throws DbException;
}
