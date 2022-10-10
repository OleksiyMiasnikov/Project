package com.myProject.dao.mysql;

import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RoleDaoTest {
    private RoleDaoImpl roleDao;
    private static Connection con;

    private static final String CREATE_ROLE_TABLE =
            "CREATE TABLE IF NOT EXISTS `role` (" +
                    "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "  `name` VARCHAR(45) NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)" +
                    "ENGINE = InnoDB";
    private static final String INSERT_DATA_IN_ROLE_TABLE =
            "INSERT INTO `role` (`id`, `name`)" +
                    "VALUES " +
                    "(1, 'admin')," +
                    "(2, 'cashier')," +
                    "(3, 'senior cashier')," +
                    "(4, 'commodity expert')";
    private static final String DROP_ROLE_TABLE = "DROP TABLE role";

    @BeforeAll
    static void globalSetUp() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/db_test";
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "18De1975");
        properties.put("autoReconnect", "true");
        properties.put("characterEncoding", "UTF-8");
        properties.put("useUnicode", "true");
        con = DriverManager.getConnection(url, properties);
    }

    @AfterAll
    static void globalTearDown() throws SQLException {
        con.close();
    }

    @BeforeEach
    void setUp() throws SQLException {
        roleDao = (RoleDaoImpl) DaoFactoryImpl.getInstance().getRoleDao();
        con.createStatement().executeUpdate(CREATE_ROLE_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_ROLE_TABLE);
    }

    @AfterEach
    void tearDown() throws SQLException {
        con.createStatement().executeUpdate(DROP_ROLE_TABLE);
    }

    @Test
    void findRoleTest() throws SQLException {
        List<Role> list = new ArrayList<>();
        list.add(new Role(1, "admin"));
        list.add(new Role(2, "cashier"));
        list.add(new Role(3, "senior cashier"));
        list.add(new Role(4, "commodity expert"));
        Collections.sort(list);
        assertEquals(list, roleDao.findAll(con, 0, 1000));
    }

    @Test
    void findByName() throws SQLException {
        Role role = new Role(1, "admin");
        assertEquals(role, roleDao.findByName(con, "admin"));
    }

    @Test
    void findByNameReturnedNull() throws SQLException {
        Assertions.assertNull(roleDao.findByName(con, "administrator"));
    }

}
