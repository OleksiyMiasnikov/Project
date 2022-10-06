package com.myProject.service.employee;

import com.myProject.entitie.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import static com.myProject.util.Constants.*;

public class CommodityExpert extends Employee {
    private static final Logger logger = (Logger) LogManager.getLogger(CommodityExpert.class);

    public CommodityExpert(User user, String locale) {
        super(user, locale, REMAINS_COMMAND, NEW_PRODUCT_COMMAND, LIST_OF_PRODUCT_COMMAND, NEW_INCOME_COMMAND, INCOMES_COMMAND, REMAINS_COMMAND);
    }
}

