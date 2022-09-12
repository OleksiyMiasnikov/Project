package com.myProject.employee;

import com.myProject.dao.entitie.User;
import com.myProject.dao.mysql.MySqlConnectionPool;
import com.myProject.exception.DbException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Employee {
    private static final Logger logger = (Logger) LogManager.getLogger(MySqlConnectionPool.class);

    private final User user;
    private final List<String> menuItems;

    public User getUser() {
        return user;
    }

    public List<String> getMenuItems() {
        return menuItems;
    }

    public Employee(User user, String ... items) {
        this.user = user;
        this.menuItems = new ArrayList<>(Arrays.asList(items));
    }

    public static Employee createEmployee(User user) throws DbException {
        switch (user.getRole().getName()) {
            case "admin": return new Admin(user);
            case "cashier": return new Cashier(user);
            case "commodity expert": return new CommodityExpert(user);
            case "senior cashier": return new SeniorCashier(user);
            default: {
                logger.error("Role is incorrect");
                throw new DbException("Role is incorrect");
            }
        }
    }
}
