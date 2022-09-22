package com.myProject.service.command;

import com.myProject.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.myProject.util.Constants.*;

public class Receiver {
    private static final Logger logger = (Logger) LogManager.getLogger(Receiver.class);
    Map<String, Command> commandMap;
    public Receiver() {
        this.commandMap = new HashMap<>();
        commandMap.put(COMMAND_ADD_USER, new AddUser());
        commandMap.put(COMMAND_SHOW_USERS, new ShowUsers());
        commandMap.put(COMMAND_UPDATE_USER, new UpdateUser());
        commandMap.put(COMMAND_DELETE_USER, new DeleteUser());
        commandMap.put(COMMAND_NEW_ORDER, new NewOrder());
        commandMap.put(COMMAND_REPORTS, new Reports());
        commandMap.put(COMMAND_NEW_PRODUCT, new NewProduct());
        commandMap.put(COMMAND_LIST_OF_PRODUCT, new ListOfProducts());
        commandMap.put(COMMAND_INCOME, new Income());
        commandMap.put(COMMAND_CREATE_DATABASE, new CreateDatabase());
        commandMap.put(COMMAND_DELETE_ORDER, new DeleteOrder());
        commandMap.put(COMMAND_DELETE_ORDER_PRODUCT, new DeleteOrderedProduct());
        commandMap.put(COMMAND_REMAINS, new Remains());
    }
    public void runCommand(HttpServletRequest req, HttpServletResponse resp, String buttonName)
            throws DaoException, ServletException, IOException {
        logger.info("runCommand executed");
        commandMap.get(buttonName).execute(req, resp);
    }
}
