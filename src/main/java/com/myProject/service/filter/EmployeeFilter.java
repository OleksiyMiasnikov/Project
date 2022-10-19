package com.myProject.service.filter;

import com.myProject.employee.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/*")
public class EmployeeFilter implements Filter {
    private static final Logger logger = (Logger) LogManager.getLogger(EmployeeFilter.class);


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Employee employee = (Employee) req.getSession().getAttribute("employee");
        if ("/controller".equals(req.getServletPath()) &&
                !"authorization".equals(req.getParameter("command")) &&
                employee == null) {
            logger.warn("Unauthorised access attempt");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
