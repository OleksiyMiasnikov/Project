package com.myProject.dao;

import com.myProject.entitie.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDao extends Dao<Long, User>{

    int findRowsTotal(Connection con) throws SQLException;
}
