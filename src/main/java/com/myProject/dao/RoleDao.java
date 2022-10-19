package com.myProject.dao;

import com.myProject.entitie.Role;

import java.sql.Connection;
import java.sql.SQLException;

public interface RoleDao extends Dao<Long, Role>{
    Role findByName(Connection con, String name) throws SQLException;

}
