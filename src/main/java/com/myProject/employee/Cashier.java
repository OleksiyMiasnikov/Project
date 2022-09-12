package com.myProject.employee;

import com.myProject.dao.entitie.User;


public class Cashier extends Employee{
    public Cashier(User user) {
        super(user, new String[]{"New order",
                                "Reports"});
    }
}
