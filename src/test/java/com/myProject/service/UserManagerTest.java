package com.myProject.service;

import com.myProject.dao.RoleDao;
import com.myProject.dao.UserDao;
import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import com.myProject.util.ConnectionPool;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import static com.myProject.TestConstants.CONNECTION_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;


public class UserManagerTest extends Mockito {
    @Mock
    private UserDao userDao;
    @Mock
    private RoleDao roleDao;
    @Mock
    private Connection con;
    @Mock
    private ConnectionPool pool;
    private UserManager manager;

    public UserManagerTest() {
        MockitoAnnotations.initMocks(this);
        this.manager = UserManager
                .getInstance(userDao, roleDao);
    }

    @BeforeAll
    static void globalSetUp(){

    }

    @AfterAll
    static void globalTearDown(){

    }

    @BeforeEach
    void setUp(){

    }

    @AfterEach
    void tearDown(){

    }

    @Test
    void test() throws SQLException {
        final Connection con = mock(Connection.class);
        final ConnectionPool pool = mock(ConnectionPool.class);
        when(pool.getConnection()).thenReturn(con);

        given(userDao.findRowsTotal(con))
                .willReturn(5);
        assertEquals(manager.findRowsTotal(), 5);
    }

}
