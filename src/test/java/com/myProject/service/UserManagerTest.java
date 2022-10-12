package com.myProject.service;

import com.myProject.dao.DaoFactory;
import com.myProject.dao.UserDao;
import com.myProject.entitie.User;
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
    @BeforeAll
    static void globalSetUp() throws SQLException {
        MockedStatic<ConnectionPool> mocked = mockStatic(ConnectionPool.class);
        ConnectionPool pool = mock(ConnectionPool.class);
        when(ConnectionPool.getInstance()).thenReturn(pool);
        when(pool.getConnection()).thenReturn(DriverManager.getConnection(CONNECTION_URL));
        manager = UserManager.getInstance(DaoFactory.getInstance().getUserDao(),
                DaoFactory.getInstance().getRoleDao());
    }

    @AfterAll
    static void globalTearDown(){
    }

    @BeforeEach
    void setUp() throws SQLException {
        Connection con = DriverManager.getConnection(CONNECTION_URL);
        con.createStatement().executeUpdate(CREATE_ROLE_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_ROLE_TABLE);
        con.createStatement().executeUpdate(CREATE_USER_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_USER_TABLE);
        con.close();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Connection con = DriverManager.getConnection(CONNECTION_URL);
        con.createStatement().executeUpdate(DROP_USER_TABLE);
        con.createStatement().executeUpdate(DROP_ROLE_TABLE);
        con.close();
    }

    @Test
    void findUserTest() throws SQLException {
        assertEquals(manager.findUser("admin").getId(), 1);
    }

/*    @Test
    void findRowsTotalTest() throws SQLException {
        assertEquals(manager.findRowsTotal(), 5);
    }*/


 /*   @Test
    void deleteAllTest() throws SQLException {
        manager.deleteAll(new String[]{"4", "5"});
        //List<User> list = manager.findAllUsers(0, 1000);
        assertEquals(manager.findRowsTotal(), 3);
        //assertEquals(list.get(0).getLogin(), "admin");
    }*/
/*    @Test
    void updateUserTest() throws SQLException {
        User user = manager.read(1L);
        user.setLogin("Taras");
        manager.updateUser(user);
        user = manager.read(1L);
        assertEquals(user.getLogin(), "Taras");
    }*/
}
