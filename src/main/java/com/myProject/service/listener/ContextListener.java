package com.myProject.service.listener;

import com.myProject.dao.DaoFactory;
import com.myProject.service.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;


@WebListener
public class ContextListener implements ServletContextListener, ServletContextAttributeListener {
    private static final Logger logger = (Logger) LogManager.getLogger(ContextListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserManager userManager =
                UserManager.getInstance(DaoFactory.getInstance().getUserDao(),
                                        DaoFactory.getInstance().getRoleDao());
        sce.getServletContext().setAttribute("UserManager", userManager);
        CommodityExpertManager commodityExpertManager =
                CommodityExpertManager.getInstance(DaoFactory.getInstance().getWarehouseDao(),
                                             DaoFactory.getInstance().getProductDao());
        sce.getServletContext().setAttribute("CommodityExpertManager", commodityExpertManager);
        CashierManager cashierManager =
                CashierManager.getInstance(DaoFactory.getInstance().getOrderDao(),
                                           DaoFactory.getInstance().getOrderDetailsDao());
        sce.getServletContext().setAttribute("CashierManager", cashierManager);

        // obtain file name with locales descriptions
        ServletContext context = sce.getServletContext();
        String localesFileName = context.getInitParameter("locales");

        // obtain real path on server
        String localesFileRealPath = context.getRealPath(localesFileName);

        // local descriptions
        Properties locales = new Properties();
        try {
            locales.load(new FileInputStream(localesFileRealPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // save descriptions to servlet context
        context.setAttribute("locales", locales);

        logger.info("locales: " + locales.toString());
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
