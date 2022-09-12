package com.myProject.employee;

import com.myProject.dao.entitie.User;

import static com.myProject.dao.Constants.*;

public class Admin extends Employee{
    public Admin(User user) {
        super(user, COMMAND_SHOW_USERS, COMMAND_ADD_USER, COMMAND_DELETE_USER);
    }
}
