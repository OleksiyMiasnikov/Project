package com.myProject.service;

import com.myProject.dao.DaoFactory;
import com.myProject.util.ConnectionPool;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.myProject.TestConstants.*;
import static com.myProject.TestConstants.DROP_ROLE_TABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserManagerTest extends Mockito {
    private static UserManager manager;
    private static Connection con;

    @BeforeAll
    static void globalSetUp() throws SQLException {
        con = DriverManager.getConnection(CONNECTION_URL);
        MockedStatic mocked = mockStatic(ConnectionPool.class);
        ConnectionPool pool = mock(ConnectionPool.class);
        when(ConnectionPool.getInstance()).thenReturn(pool);
        when(pool.getConnection()).thenReturn(con);
        manager = UserManager.getInstance(DaoFactory.getInstance().getUserDao(),
                DaoFactory.getInstance().getRoleDao());
    }

    @AfterAll
    static void globalTearDown() throws SQLException {

    }

    @BeforeEach
    void setUp() throws SQLException {
        con.createStatement().executeUpdate(CREATE_ROLE_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_ROLE_TABLE);
        con.createStatement().executeUpdate(CREATE_USER_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_USER_TABLE);
    }

    @AfterEach
    void tearDown() throws SQLException {
        con = DriverManager.getConnection(CONNECTION_URL);
        con.createStatement().executeUpdate(DROP_USER_TABLE);
        con.createStatement().executeUpdate(DROP_ROLE_TABLE);
        con.close();
    }

    @Test
    void test() throws SQLException {
        assertEquals(manager.findRowsTotal(), 5);
    }

}
