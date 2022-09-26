package com.myProject.service.employee;

import com.myProject.dao.entitie.User;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.myProject.util.Constants.*;

public class Admin extends Employee{
    private static final Logger logger = (Logger) LogManager.getLogger(Admin.class);
    public Admin(User user) {
        super(user, "/AdminFragment", ADD_USER_COMMAND);
    }

    public void initWindow(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        logger.info("initWindow");
        req.setAttribute("Fragment", "/AdminFragment");
        try {
            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
