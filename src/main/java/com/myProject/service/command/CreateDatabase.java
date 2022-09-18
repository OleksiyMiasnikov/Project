package com.myProject.service.command;

import com.myProject.exception.DaoException;
import com.myProject.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateDatabase implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(CreateDatabase.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        logger.info("Start creating database");

        String createSql = readScript("sql\\create.sql");
        String insertData = readScript("sql\\insertions.sql");



        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            Statement stmt = con.createStatement();
            stmt.execute(createSql);
            stmt.execute(insertData);
            logger.info("Finish creating database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                logger.error("Can not close connection!" + e);
            }
        }


    }

    private String readScript(String filePath) {
        logger.info("read script from " + filePath);
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filePath).getFile());
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader reader
                    = new BufferedReader(new FileReader(file));
            String str;
           while ((str = reader.readLine()) != null) {
               buffer.append(str).append(System.lineSeparator());
           }
           logger.info(buffer);
           return buffer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
