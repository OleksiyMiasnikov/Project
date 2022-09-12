package com.myProject.dao;

import com.myProject.dao.entitie.User;
import com.myProject.exception.DbException;

import java.sql.Connection;

public interface UserDao {
    public User findUser(Connection con, String login) throws DbException;

}
