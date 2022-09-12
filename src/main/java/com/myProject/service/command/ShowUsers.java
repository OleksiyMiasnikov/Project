package com.myProject.service.command;

import com.myProject.dao.entitie.User;
import com.myProject.exception.DbException;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowUsers implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ShowUsers.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws DbException, ServletException, IOException {
        logger.info("Start execute command");
        UserManager userManager = (UserManager) req.getSession().getServletContext().getAttribute("UserManager");
        List<User> userList = userManager.findAllUsers();
        req.setAttribute("result", userList);
        logger.info("Finish execute command");
        req.getRequestDispatcher("main").forward(req, resp);
        //resp.sendRedirect("main");
    }
}
