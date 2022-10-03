package com.myProject.service.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {
    private static final Logger logger = (Logger) LogManager.getLogger(SessionListener.class);
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
/*        logger.info("Added session attribute (Session: "
                + event.getSession()
                + ", attribute "
                + event.getName()
                + "="
                + event.getValue()
                + ")");*/
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
/*        logger.info("Removed session attribute (Session: "
                + event.getSession()
                + ", attribute "
                + event.getName()
                + "="
                + event.getValue()
                + ")");*/
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        /*logger.info("Replaced session attribute (Session: "
                + event.getSession()
                + ", attribute "
                + event.getName()
                + "="
                + event.getValue()
                + ")");*/
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setAttribute("locale",
                event.getSession()
                    .getServletContext()
                    .getInitParameter("javax.servlet.jsp.jstl.fmt.locale"));
    /*    logger.info("Session created: "
                + se.getSession()
                + ". Employee: "
                + se.getSession().getAttribute("employee"));*/
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
/*        logger.info("Session destroyed: "
                + se.getSession() + "-" + se.getSession().getAttribute("employee") + "-" + se.getSession().getAttribute("Login"));

 */
    }
}
