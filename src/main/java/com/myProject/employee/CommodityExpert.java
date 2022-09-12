package com.myProject.employee;

import com.myProject.dao.entitie.User;

public class CommodityExpert extends Employee {

    public CommodityExpert(User user) {
        super(user, new String[]{"New goods",
                                "New incoming",
                                "New outgoing",
                                "Reports"});
    }
}

