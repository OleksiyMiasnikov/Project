package com.myProject.employee;

import com.myProject.dao.entitie.User;

public class Admin extends Employee{
    public Admin(User user) {
        super(user, new String[]{"Add user", "Delete user"});
    }
}
