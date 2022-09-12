package com.myProject.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class RequestListener implements ServletRequestListener, ServletRequestAttributeListener {
    private static final Logger logger = (Logger) LogManager.getLogger(ServletRequestListener.class);
    @Override
    public void attributeAdded(ServletRequestAttributeEvent srae) {
        //logger.info("Added request attribute (" + srae.getName() + "=" + srae.getValue() + ")");
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent srae) {
       // logger.info("Removed request attribute (" + srae.getName() + "=" + srae.getValue() + ")");
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent srae) {
       // logger.info("Replaced request attribute (" + srae.getName() + "=" + srae.getValue() + ")");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
       // logger.info("Request is destroyed: " + sre.getServletRequest().getLocalName());
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
       // logger.info("Request is initialized: " + sre.getServletRequest().getLocalName());
    }
}
