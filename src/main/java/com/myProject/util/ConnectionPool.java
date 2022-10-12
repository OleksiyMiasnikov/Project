package com.myProject.util;

import com.myProject.service.exception.DaoException;
import static com.myProject.util.Constants.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final Logger logger = (Logger) LogManager.getLogger(ConnectionPool.class);
    private ConnectionPool(){
    }
    private static ConnectionPool instance = null;

    public static synchronized ConnectionPool getInstance(){
        if (instance==null) {
            instance = new ConnectionPool();
            logger.info("Instance of ConnectionPool created");
        }
        return instance;
    }

    public Connection getConnection() throws DaoException {
        Connection connection;
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup(CONTEXT_NAME);
            connection = dataSource.getConnection();
        } catch (NamingException | SQLException e) {
            logger.error("Can not get connection. " + e);
            throw new DaoException("Can not get connection", e);
        }
        return connection;
    }
}
