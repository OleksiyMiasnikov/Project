package com.myProject.employee;

import com.myProject.dao.entitie.User;
import static com.myProject.dao.Constants.*;


public class Cashier extends Employee{
    public Cashier(User user) {
        super(user, COMMAND_NEW_ORDER, COMMAND_REPORTS);
    }
}
