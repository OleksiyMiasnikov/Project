package com.myProject.employee;

import com.myProject.dao.entitie.User;

public class SeniorCashier extends Employee{
    public SeniorCashier(User user) {
        super(user, new String[]{"New order",
                                "Reports"});
    }
}
