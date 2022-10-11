package com.myProject.dao.mysql;

import com.myProject.entitie.Role;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myProject.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class RoleDaoTest {
    private RoleDaoImpl roleDao;
    private static Connection con;
    @BeforeAll
    static void globalSetUp() throws SQLException {
        con = DriverManager.getConnection(CONNECTION_URL);
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
