package com.myProject.employee;

import com.myProject.entitie.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.List;

import static com.myProject.util.Constants.*;

public class CommodityExpert extends Employee {
    private static final Logger logger = (Logger) LogManager.getLogger(CommodityExpert.class);

    public CommodityExpert(User user) {
        super(user,
                REMAINS_COMMAND,
                List.of(NEW_PRODUCT_COMMAND,
                        LIST_OF_PRODUCT_COMMAND,
                        NEW_INCOME_COMMAND,
                        INCOMES_COMMAND,
                        REMAINS_COMMAND),
                List.of(NEW_PRODUCT_COMMAND,
                        CANCEL_ORDER_COMMAND,
                        COMPLETE_ORDER_COMMAND,
                        BACK_COMMAND,
                        LOCALE_COMMAND,
                        LOGOUT_COMMAND,
                        MOVIES_COMMAND,
                        LIST_OF_PRODUCT_COMMAND,
                        AUTHORIZATION_COMMAND,
                        SERVE_ORDER_COMMAND,
                        NEW_INCOME_COMMAND,
                        NEW_ORDER_COMMAND,
                        INCOMES_COMMAND,
                        REMAINS_COMMAND));
        logger.info("Commodity Expert created");
    }
}

