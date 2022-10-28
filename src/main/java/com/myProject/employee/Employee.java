package com.myProject.employee;

import com.myProject.entitie.User;
import com.myProject.util.ConnectionPool;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.*;

import static com.myProject.util.Constants.PATH;

public abstract class Employee implements Serializable {
    private static final Logger logger = (Logger) LogManager.getLogger(ConnectionPool.class);
    private final User user;
    private Deque<List<String>> stackOfMenuItems;
    private Deque<String> stackOfPages;
    private List<String> availableCommands;
    private final String startCommand;

    public Employee(User user, String startCommand, List<String> items, List<String> availableCommands) {
        this.user = user;
        this.startCommand = startCommand;
        stackOfMenuItems = new ArrayDeque<>();
        stackOfMenuItems.push(items);
        stackOfPages = new ArrayDeque<>();
        this.availableCommands = availableCommands;
    }

    public User getUser() {
        return user;
    }
    public List<String> getMenuItems() {
        return stackOfMenuItems.peek();
    }
    public void popMenuItems() {
        stackOfMenuItems.pop();
    }

    public void setMenuItems(List<String> menuItems) {
        stackOfMenuItems.push(menuItems);
    }

    public String getPage() {
        return stackOfPages.peek();
    }

    public String popPage() {
        return stackOfPages.pop();
    }

    public void setStackOfPages(String commandName) {
        stackOfPages.push(commandName);
    }

    public static Employee createEmployee(User user) throws DaoException {
        switch (user.getRole().getName()) {
            case "admin": return new Admin(user);
            case "cashier": return new Cashier(user);
            case "commodity expert": return new CommodityExpert(user);
            case "senior cashier": return new SeniorCashier(user);
            default: {
                logger.error("Role is incorrect");
                throw new DaoException("Role is incorrect");
            }
        }
    }

    public static void manuUp(HttpSession session, List<String> listOfCommand) {
        Employee employee = (Employee) session.getAttribute("employee");
        employee.setMenuItems(listOfCommand);
        employee.setStackOfPages((String) session.getAttribute("previous_command"));
        session.setAttribute("employee", employee);
    }

    public String getStartCommand() {
        return startCommand;
    }

    public List<String> getAvailableCommands() {
        return availableCommands;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return user.equals(employee.user) &&
                startCommand.equals(employee.startCommand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, stackOfMenuItems, startCommand);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "user=" + user +
                ", menuItems=" + stackOfMenuItems.peek() +
                ", startCommand='" + startCommand + '\'' +
                '}';
    }
}
