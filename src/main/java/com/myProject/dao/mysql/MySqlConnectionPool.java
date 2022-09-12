package com.myProject.dao.mysql;

import com.myProject.exception.DbException;
import static com.myProject.dao.Constants.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySqlConnectionPool {
    private static final Logger logger = (Logger) LogManager.getLogger(MySqlConnectionPool.class);
    private MySqlConnectionPool(){
        // ...
    }
    private static MySqlConnectionPool instance = null;

    public static MySqlConnectionPool getInstance(){
        if (instance==null) {
            instance = new MySqlConnectionPool();
            logger.info("Instance of ConnectionPool created");
        }
        return instance;
    }

    public Connection getConnection() throws DbException {
        Context context;
        Connection connection;
        try {
            context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup(CONTEXT_NAME);
            connection = dataSource.getConnection();
        } catch (NamingException | SQLException e) {
            logger.error("Can not get connection");
            throw new DbException("Can not get connection", e);
        }
        return connection;
    }

    public static void destroyConnectionPool() {
        //????
    }

}
