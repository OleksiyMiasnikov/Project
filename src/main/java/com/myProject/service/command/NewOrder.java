package com.myProject.service.command;

import com.myProject.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewOrder implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(NewOrder.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("--- NewOrder ---");
        //Get order

        req.getRequestDispatcher("jsp/order.jsp").forward(req, resp);
    }
}
