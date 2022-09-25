package com.myProject.service.command;

import com.myProject.exception.DaoException;
import com.myProject.service.CashierManager;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.myProject.util.Constants.COMMAND_SHOW_USERS;

public class DeleteUser implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(DeleteUser.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        logger.info("execute");
        String[] users = req.getParameterMap().get("users");
        if (users != null) {
            UserManager userManager = (UserManager) req.getServletContext().getAttribute("UserManager");
            userManager.deleteAll(users);
            /*CashierManager cashierManager = (CashierManager) req.getServletContext().getAttribute("CashierManager");
            cashierManager.deleteAll(orders);*/
        }
        logger.info("finished ");

        /*String userLogin = req.getParameter("users");
        if (userLogin == null || userLogin.equals("")) {
            try {
                req.getRequestDispatcher("user_details.jsp").forward(req, resp);
            } catch (IOException | ServletException e) {
                throw new RuntimeException(e);
            }
            return userLogin;
        }
        logger.info("Start deleting user: " + userLogin);
        UserManager userManager = (UserManager) req.getServletContext().getAttribute("UserManager");
        if (userManager.deleteUser(userLogin)) {
            logger.info("User: " + userLogin + "has been deleted");
        } else {
            logger.info("Unable to deleteOrder user: " + userLogin);
        }*/
        return "controller?command=" + COMMAND_SHOW_USERS;
    }
}
