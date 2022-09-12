package com.myProject.dao.entitie;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User implements Serializable {
    private long id;
    private String login;
    private String password;
    private String email;
    private Role role;

    public static User newInstance(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(1));
        user.setLogin(resultSet.getString(2));
        user.setPassword(resultSet.getString(3));
        user.setEmail(resultSet.getString(4));
        user.setRole(new Role(resultSet.getInt(5), resultSet.getString(6)));
        return user;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
