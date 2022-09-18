package com.myProject.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter("/*")
public class SessionActiveFilter implements Filter {
    private static final Logger logger = (Logger) LogManager.getLogger(SessionActiveFilter.class);


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       HttpServletRequest req = (HttpServletRequest) servletRequest;
       HttpSession session = req.getSession();
    //   logger.info("Session: " + session);
       filterChain.doFilter(servletRequest, servletResponse);
    }
}
