package com.myProject.filter;

import com.myProject.dao.entitie.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


//@WebFilter("/*")
public class RoleFilter implements Filter {
    private static final Logger logger = (Logger) LogManager.getLogger(RoleFilter.class);

   // private UserManager userManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       // userManager = (UserManager) filterConfig.getServletContext().getAttribute("UserManager");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        logger.info("RoleFilter started");
//        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//        HttpSession session = httpRequest.getSession(false);
//        if (session != null) {
//            User user = (User) session.getAttribute("CurrentUser");
//            if (!"admin".equals(user.getRole().getName())) {
//                logger.warn("User is not an administrator!");
//            }
//        }
       logger.info("RoleFilter finished");
       filterChain.doFilter(servletRequest, servletResponse);
    }
}
