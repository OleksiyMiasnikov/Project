package com.myProject.employee;

import com.myProject.entitie.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import static com.myProject.util.Constants.*;

public class Admin extends Employee{
    private static final Logger logger = (Logger) LogManager.getLogger(Admin.class);
    public Admin(User user) {
        super(user, SHOW_USERS_COMMAND, ADD_USER_COMMAND);
    }
}
