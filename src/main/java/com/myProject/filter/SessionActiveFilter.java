package com.myProject.filter;

import com.myProject.employee.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter("/*")
public class SessionActiveFilter implements Filter {
    private static final Logger logger = (Logger) LogManager.getLogger(SessionActiveFilter.class);


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Employee employee = (Employee) req.getSession().getAttribute("Employee");
        if (employee == null) {
            if (req.getHeader("referer") == null ) {
                logger.warn("Unauthorized access attempt");
                req.getRequestDispatcher("index.html").forward(req, resp);
            } else {
                req.getRequestDispatcher("authorization").forward(req, resp);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
