package com.myProject.employee;

import com.myProject.dao.entitie.User;
import static com.myProject.dao.Constants.*;

public class CommodityExpert extends Employee {

    public CommodityExpert(User user) {
        super(user, COMMAND_NEW_GOODS, COMMAND_INCOME, COMMAND_REPORTS);
    }
}

