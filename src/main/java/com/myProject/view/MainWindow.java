package com.myProject.view;

import com.myProject.dao.entitie.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/main")
public class MainWindow extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(MainWindow.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        HttpSession session = req.getSession();
        logger.info("View layer. Session: " + session.getId());
        Arrays.stream(req.getCookies()).forEach(cookie -> logger.info("Cookie = " + cookie.getName() + ": " + cookie.getValue()));
        User user = (User) session.getAttribute("CurrentUser");
        session.setAttribute("result", "User: "
                                            + user.getLogin()
                                            + "<br>Has role: "
                                            + user.getRole().getName());
        req.getRequestDispatcher("WEB-INF/Pages/page.jsp").forward(req, resp);
    }
}
