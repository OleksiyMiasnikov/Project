package com.myProject.service.command;

import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Income implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(Income.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("--- Income ---");
        // 1. input form  : product id, product name, quantity
        // 2. increase quantity in warehouse if product exist
        // 3. insert product if it is not exist in warehouse
        return "serveIncome";
    }
}
