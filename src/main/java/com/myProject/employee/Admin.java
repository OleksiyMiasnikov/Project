package com.myProject.employee;

import com.myProject.entitie.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.List;

import static com.myProject.util.Constants.*;

public class Admin extends Employee{
    private static final Logger logger = (Logger) LogManager.getLogger(Admin.class);
    public Admin(User user) {
        super(user, SHOW_USERS_COMMAND,
                List.of(ADD_USER_COMMAND),
                List.of(ADD_USER_COMMAND,
                        ADD_USER_DETAILS_COMMAND,
                        BACK_COMMAND,
                        LOCALE_COMMAND,
                        DELETE_USER_COMMAND,
                        LOGOUT_COMMAND,
                        SHOW_USERS_COMMAND,
                        AUTHORIZATION_COMMAND,
                        UPDATE_USER_COMMAND));
        logger.info("Admin created");
    }
}
