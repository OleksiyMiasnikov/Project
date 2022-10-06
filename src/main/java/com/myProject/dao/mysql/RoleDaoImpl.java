package com.myProject.dao.mysql;

import com.myProject.dao.RoleDao;
import com.myProject.entitie.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myProject.util.Constants.*;

public class RoleDaoImpl implements RoleDao {
    @Override
    public List<Role> findAll(Connection con, int from, int size) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        List<Role> rolesList = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(SELECT_ALL_ROLES_WITH_LIMIT);
            pstmt.setInt(1, from);
            pstmt.setInt(2, size);
            pstmt.executeQuery();
            resultSet = pstmt.getResultSet();
            while (resultSet.next()) {
                rolesList.add(Role.newInstance(resultSet));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
        Collections.sort(rolesList);
        return rolesList;
    }

    @Override
    public Role findByName(Connection con, String name) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = con.prepareStatement(GET_ROLE_ID);
            pstmt.setString(1, name);
            pstmt.executeQuery();
            resultSet = pstmt.getResultSet();
            if (resultSet.next()) {
                return new Role(resultSet.getLong(1), name);
            } else {
                return null;
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
    }

    @Override
    public Role read(Connection con, Long id){
        return null;
    }

    @Override
    public Role create(Connection con, Role entity){
        return null;
    }

    @Override
    public void update(Connection con, Role entity){

    }

    @Override
    public boolean delete(Connection con, Long id){
        return false;
    }
}
