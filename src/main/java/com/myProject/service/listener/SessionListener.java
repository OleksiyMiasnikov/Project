package com.myProject.service.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setAttribute("locale",
                                            event.getSession()
                                                .getServletContext()
                                                .getInitParameter("javax.servlet.jsp.jstl.fmt.locale"));
    }

}
