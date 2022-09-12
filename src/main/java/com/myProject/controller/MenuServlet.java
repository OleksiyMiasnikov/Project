package com.myProject.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(MenuServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Start");
        List<String> menuItems = new ArrayList<>();
        menuItems.add("New goods");
        menuItems.add("New incoming");
        menuItems.add("New outgoing");
        menuItems.add("Reports");
        req.setAttribute("menuItems", menuItems);
        req.setAttribute("menuTitle", "Commodity expert menu");
        this.getServletConfig().getServletContext().setAttribute("menuItems", menuItems);
        this.getServletConfig().getServletContext().setAttribute("menuTitle", "--Commodity expert menu--");



        req.getRequestDispatcher("WEB-INF/Pages/menuBar.jsp").forward(req, resp);
        logger.info("Finish");
    }
}
