package com.myProject.service.command;

import com.myProject.exception.DbException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Receiver {
    private static final Logger logger = (Logger) LogManager.getLogger(Receiver.class);
    Map<String, Command> commandMap;
    public Receiver() {
        this.commandMap = new HashMap<>();
        commandMap.put("Add user", new AddUser());
        commandMap.put("Show users", new ShowUsers());
        commandMap.put("Delete user", new DeleteUser());
    }
    public void runCommand(HttpServletRequest req, HttpServletResponse resp, String buttonName) throws DbException, ServletException, IOException {
        commandMap.get(buttonName).execute(req, resp);
    }
}
