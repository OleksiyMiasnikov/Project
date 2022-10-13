package com.myProject.employee;

import com.myProject.entitie.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import static com.myProject.util.Constants.*;

public class SeniorCashier extends Employee{
    private static final Logger logger = (Logger) LogManager.getLogger(SeniorCashier.class);
    public SeniorCashier(User user) {
        super(user, ORDERS_COMMAND, NEW_ORDER_COMMAND, ORDERS_COMMAND, REMAINS_COMMAND, X_REPORT_COMMAND, Z_REPORT_COMMAND);
        logger.info("Senior Cashier created");
    }
}
