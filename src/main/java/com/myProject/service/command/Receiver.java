package com.myProject.service.command;

import com.myProject.employee.Employee;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.myProject.util.Constants.*;

/**
 * class Receiver has map with keys as names of commands and values instances of the corresponding commands classes
 */
public class Receiver {
    private static final Map<String, Command> commandMap;

    static {
        commandMap = new HashMap<>();
        commandMap.put(ADD_USER_COMMAND, new NewUser());
        commandMap.put(ADD_USER_DETAILS_COMMAND, new AddUserDetails());
        commandMap.put(AUTHORIZATION_COMMAND, new Authorization());
        commandMap.put(BACK_COMMAND, new Back());
        commandMap.put(CANCEL_ORDER_COMMAND, new CancelOrder());
        commandMap.put(COMPLETE_ORDER_COMMAND, new CompleteOrder());
        commandMap.put(CREATE_PRODUCT_COMMAND, new CreateProduct());
        commandMap.put(DELETE_ORDER_COMMAND, new DeleteOrder());
        commandMap.put(DELETE_ORDER_PRODUCT_COMMAND, new DeleteOrderedProduct());
        commandMap.put(DELETE_USER_COMMAND, new DeleteUser());
        commandMap.put(INCOMES_COMMAND, new Incomes());
        commandMap.put(LIST_OF_PRODUCT_COMMAND, new ListOfProducts());
        commandMap.put(LOCALE_COMMAND, new ChangeLocale());
        commandMap.put(LOGOUT_COMMAND, new Logout());
        commandMap.put(MOVIES_COMMAND, new Movies());
        commandMap.put(NEW_INCOME_COMMAND, new NewIncome());
        commandMap.put(NEW_ORDER_COMMAND, new NewOrder());
        commandMap.put(NEW_PRODUCT_COMMAND, new ServeProduct());
        commandMap.put(ORDERS_COMMAND, new Orders());
        commandMap.put(REMAINS_COMMAND, new Remains());
        commandMap.put(PREPARING_EMAIL_COMMAND, new PreparingEmail());
        commandMap.put(SEND_EMAIL_COMMAND, new SendEmail());
        commandMap.put(SERVE_ORDER_COMMAND, new ServeOrder());
        commandMap.put(SHOW_USERS_COMMAND, new ShowUsers());
        commandMap.put(UPDATE_USER_COMMAND, new UpdateUser());
        commandMap.put(X_REPORT_COMMAND, new ReportX());
        commandMap.put(Z_REPORT_COMMAND, new ReportZ());
    }

    /**
     * checks if the requested command is allowed to the employee of the current session
     * @return instance of command implementation which the specified to parameter 'commandName'
     * @throws AppException with the message "Unauthorised access attempt" if the command is not allowed
     */
    public static String runCommand(HttpServletRequest req, HttpServletResponse resp, String commandName)
            throws DaoException, AppException {
        Employee employee = (Employee) req.getSession().getAttribute("employee");
        if (employee == null && "authorization".equals(commandName) ||
            employee != null && employee.getAvailableCommands().contains(commandName)) {
            return commandMap.get(commandName).execute(req, resp);
        }
        throw new AppException("Unauthorised access attempt");
    }
}
