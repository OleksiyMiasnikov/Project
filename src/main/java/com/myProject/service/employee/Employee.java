package com.myProject.service.employee;

import com.myProject.dao.entitie.User;
import com.myProject.service.command.Command;
import com.myProject.util.ConnectionPool;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Employee implements Serializable {
    private static final Logger logger = (Logger) LogManager.getLogger(ConnectionPool.class);
    private final User user;
    private final List<String> menuItems;
    private final String startCommand;
    private String locale;

    public User getUser() {
        return user;
    }

    public Employee(User user, String locale, String startCommand, String ... items) {
        this.user = user;
        this.locale = locale;
        this.startCommand = startCommand;
        this.menuItems = new ArrayList<>(Arrays.asList(items));
    }
    public List<String> getMenuItems() {
        return menuItems;
    }

    public String getStartCommand() {
        return startCommand;
    }

    public static Employee createEmployee(User user, String locale) throws DaoException {
        switch (user.getRole().getName()) {
            case "admin": return new Admin(user, locale);
            case "cashier": return new Cashier(user, locale);
            case "commodity expert": return new CommodityExpert(user, locale);
            case "senior cashier": return new SeniorCashier(user, locale);
            default: {
                logger.error("Role is incorrect");
                throw new DaoException("Role is incorrect");
            }
        }
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "user=" + user +
                ", menuItems=" + menuItems +
                ", startCommand='" + startCommand + '\'' +
                ", locale='" + locale + '\'' +
                '}';
    }
}
