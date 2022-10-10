package com.myProject.dao.mysql;

import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import com.myProject.service.exception.DaoException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test {
    private static final String CONNECTION_URL = "jdbc:derby:memory:test_db;create=true";

    private static final String SHUTDOWN_URL = "jdbc:derby:;shutdown=true";

    private static final String APP_PROPS_FILE = "app.properties";

    private static final String APP_CONTENT = "connection.url=" + CONNECTION_URL;

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS `user` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT, " +
                    "`login` VARCHAR(45) NULL, " +
                    "`password` VARCHAR(45) NULL, " +
                    "`email` VARCHAR(100) NULL, " +
                    "`role_id` INT UNSIGNED NOT NULL, " +
                "PRIMARY KEY (`id`, `role_id`), " +
                "UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE, " +
                "UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE, " +
                "INDEX `fk_users_roles1_idx` (`role_id` ASC) VISIBLE, " +
                "CONSTRAINT `fk_users_roles1` " +
                "FOREIGN KEY (`role_id`) " +
                "REFERENCES `cash_register`.`role` (`id`) " +
                "ON DELETE NO ACTION " +
                "ON UPDATE NO ACTION) " +
                "ENGINE = InnoDB";

    private static final String DROP_USER_TABLE = "DROP TABLE user";
    private static final String DERBY_LOG_FILE = "derby.log";

    private static Connection con;

    private static String userDefinedAppContent;

    @BeforeAll
    static void globalSetUp() throws SQLException, IOException {
        System.out.println("Global setUp start");

        //userDefinedAppContent = Files.readString(Path.of(APP_PROPS_FILE));
        //Files.write(Path.of(APP_PROPS_FILE), APP_CONTENT.getBytes());

        con = DriverManager.getConnection("jdbc:derby:memory:test_db;create=true");
        System.out.println("Global setUp finish");
    }

    @AfterAll
    static void globalTearDown() throws SQLException, IOException {
        con.close();
        try {
            DriverManager.getConnection(SHUTDOWN_URL);
        } catch (SQLException ex) {
            System.err.println("Derby shutdown");
        }
        Files.delete(Path.of(DERBY_LOG_FILE));
    }

    private UserDaoImpl userDao;

    @BeforeEach
    void setUp() throws SQLException {
        System.out.println("setUp");
        userDao = (UserDaoImpl) DaoFactoryImpl.getInstance().getUserDao();
        con.createStatement().executeUpdate(CREATE_USER_TABLE);
    }

    @AfterEach
    void tearDown() throws SQLException {
        System.out.println("tearDown");
        con.createStatement().executeUpdate(DROP_USER_TABLE);
    }

    @org.junit.jupiter.api.Test
    void userCreateAndCheckEqualityTest() throws SQLException {
        System.out.println("");
        User user = new User(0, "Ivanov", "", "i@i", new Role(0, "admin"));
        User user2 = userDao.create(con, user);
        assertEquals("Ivanov",  user.getLogin());
        assertEquals(user, user2, "Two users must be equaled if their logins are equaled");
    }




}
