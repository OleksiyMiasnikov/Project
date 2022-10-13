package com.myProject.service;

import com.myProject.dao.DaoFactory;
import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import com.myProject.service.exception.DaoException;
import com.myProject.util.ConnectionPool;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static com.myProject.TestConstants.*;
import static com.myProject.TestConstants.DROP_ROLE_TABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class UserManagerTest extends BeforeMockTests {
    private static UserManager manager;
    @BeforeAll
    static void globalSetUp(){
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
        setConnection();
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


    @Test
    void findRowsTotalTest() throws SQLException {
        assertEquals(manager.findRowsTotal(), 5);
    }

    @Test
    void deleteAllTest() throws SQLException {
        manager.deleteAll(new String[]{"4", "5"});
        setConnection();
        List<User> list = manager.findAllUsers(0, 1000);
        setConnection();
        assertEquals(manager.findRowsTotal(), 3);
        setConnection();
        assertEquals(list.get(0).getLogin(), "admin");
    }

    @Test
    void updateUserTest() throws SQLException {
        User user = manager.read(1L);
        user.setLogin("Taras");
        setConnection();
        manager.updateUser(user);
        setConnection();
        user = manager.read(1L);
        assertEquals(user.getLogin(), "Taras");
    }

    @Test
    void addUserTest() throws SQLException {
        User user = User.builder()
                .id(0L)
                .login("Fedor")
                .password("01234")
                .email("fedor@gom")
                .role(Role.builder()
                        .id(2)
                        .name("cashier")
                        .build())
                .build();
        user = manager.addUser(user);
        setConnection();
        User newUser = manager.read(user.getId());
        assertEquals(newUser, user);
    }

    @Test
    void addDuplicateUserTest() throws SQLException {
        User user = User.builder()
                .id(0L)
                .login("Alex")
                .password("01234")
                .email("fedor@gom")
                .role(Role.builder()
                        .id(2)
                        .name("cashier")
                        .build())
                .build();
        assertThrows(DaoException.class, () -> manager.addUser(user));
    }

    @Test
    void findAllRolesTest() throws SQLException {
        assertEquals(manager.findAllRoles(0, 1000).size(), 4);
    }

    @Test
    void getIdRoleTest() throws SQLException {
        assertEquals(manager.getIdRole("senior cashier"), 3);
    }


    private void setConnection() throws SQLException {
     ConnectionPool pool = mock(ConnectionPool.class);
     when(ConnectionPool.getInstance()).thenReturn(pool);
     when(pool.getConnection()).thenReturn(DriverManager.getConnection(CONNECTION_URL));
    }

}
