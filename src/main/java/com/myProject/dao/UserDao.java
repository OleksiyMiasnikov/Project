package com.myProject.dao;

import com.myProject.dao.entitie.Role;
import com.myProject.dao.entitie.User;
import com.myProject.exception.DbException;

import java.sql.Connection;
import java.util.List;

public interface UserDao {
    User findUser(Connection con, String login) throws DbException;

    List<User> findAllUsers(Connection con) throws DbException;

    List<Role> findAllRoles(Connection con) throws DbException;

    boolean addUser(Connection con, User newUser);

    long getIdRole(Connection con, String name) throws DbException;

    boolean deleteUser(Connection con, String userLogin);

    void updateUser(Connection con, User userLogin);
}
