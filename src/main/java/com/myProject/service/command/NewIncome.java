package com.myProject.service.command;

import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.myProject.util.Constants.*;
/**
 * Implementation of
 */
public class NewIncome implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(NewIncome.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("New income");
        return "controller?command=" + NEW_ORDER_COMMAND + "&direction=IN&page=" + req.getParameter("page");
    }
}
