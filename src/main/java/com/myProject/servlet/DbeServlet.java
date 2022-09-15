package com.myProject.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dbe")
public class DbeServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(DbeServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().append("DB Exception");
        logger.info("DB Exception");
    }
}
