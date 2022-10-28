package com.myProject.employee;

import com.myProject.entitie.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.List;

import static com.myProject.util.Constants.*;

public class SeniorCashier extends Employee{
    private static final Logger logger = (Logger) LogManager.getLogger(SeniorCashier.class);
    public SeniorCashier(User user) {
        super(user, ORDERS_COMMAND,
                List.of(NEW_ORDER_COMMAND,
                        ORDERS_COMMAND,
                        REMAINS_COMMAND,
                        X_REPORT_COMMAND,
                        Z_REPORT_COMMAND),
                List.of(BACK_COMMAND,
                        CANCEL_ORDER_COMMAND,
                        COMPLETE_ORDER_COMMAND,
                        LOCALE_COMMAND,
                        LOGOUT_COMMAND,
                        NEW_ORDER_COMMAND,
                        ORDERS_COMMAND,
                        REMAINS_COMMAND,
                        DELETE_ORDER_COMMAND,
                        DELETE_ORDER_PRODUCT_COMMAND,
                        AUTHORIZATION_COMMAND,
                        SERVE_ORDER_COMMAND,
                        X_REPORT_COMMAND,
                        Z_REPORT_COMMAND,
                        PREPARING_EMAIL_COMMAND,
                        SEND_EMAIL_COMMAND,
                        MOVIES_COMMAND));
        logger.info("Senior Cashier created");
    }
}
