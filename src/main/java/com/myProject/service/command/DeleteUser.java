package com.myProject.service.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUser implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(DeleteUser.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("--- DeleteUser ---");
    }
}
