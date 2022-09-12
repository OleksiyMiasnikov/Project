package com.myProject.listener;

import com.myProject.dao.DaoFactory;
import com.myProject.dao.UserDao;
import com.myProject.service.UserManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.DriverManager;
import java.sql.SQLException;


@WebListener
public class ContextListener implements ServletContextListener, ServletContextAttributeListener {
    private static final Logger logger = (Logger) LogManager.getLogger(ContextListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Context \"UserManager\" added");
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        UserManager userManager = UserManager.getInstance(userDao);
        sce.getServletContext().setAttribute("UserManager", userManager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Close all resources
        try {
            DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
        } catch (SQLException e) {
            logger.error("Can not deregister driver JDBC");
        }
        logger.info("Context is destroyed. Closing all resources");
    }

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        //logger.info("Added context attribute (" + event.getName() + "=" + event.getValue() + ")");
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
      //  logger.info("Removed context attribute (" + event.getName() + "=" + event.getValue() + ")");
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
       // logger.info("Replaced context attribute (" + event.getName() + "=" + event.getValue() + ")");
    }
}
