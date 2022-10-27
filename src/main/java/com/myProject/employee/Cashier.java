package com.myProject.employee;

import com.myProject.entitie.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.List;

import static com.myProject.util.Constants.*;


public class Cashier extends Employee{
    private static final Logger logger = (Logger) LogManager.getLogger(Cashier.class);
    public Cashier(User user) {
        super(user, ORDERS_COMMAND,
                List.of(NEW_ORDER_COMMAND,
                        ORDERS_COMMAND,
                        REMAINS_COMMAND),
                List.of(BACK_COMMAND,
                        LOCALE_COMMAND,
                        LOGOUT_COMMAND,
                        NEW_ORDER_COMMAND,
                        ORDERS_COMMAND,
                        AUTHORIZATION_COMMAND,
                        REMAINS_COMMAND,
                        MOVIES_COMMAND));
        logger.info("Cashier created");
    }
}
