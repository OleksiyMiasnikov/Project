package com.myProject.service;

import com.myProject.dao.DaoFactory;
import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import com.myProject.service.exception.DaoException;
import com.myProject.util.ConnectionPool;
import org.junit.jupiter.api.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static com.myProject.util.Constants.CONTEXT_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserManagerTest {

    private UserManager manager;

    @BeforeAll
    static void globalSetUp() {
    }

    @AfterAll
    static void globalTearDown() {
    }

    @BeforeEach
    void setUp()  {
        manager = UserManager
                .getInstance(DaoFactory.getInstance().getUserDao(),
                             DaoFactory.getInstance().getRoleDao());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test() throws SQLException, ClassNotFoundException, NoSuchMethodException {
      /*  Class<?> _class = Class.forName("com.myProject.util.ConnectionPool");
       // Constructor<?> _constructor = _class.getConstructor(new Class<?>[]{String.class});
        Method method = _class.getMethod("getConnection",null);*/


/*
        ConnectionPool pool = new ConnectionPool() {
            @Override
            public Connection getConnection() throws DaoException {
                return DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test?user=root&password=18De1975");
            }
        }*/
        /*User user1 = new User(0, "Petrenko", "", "p@p", new Role(1, "admin"));
        user1 = manager.addUser(user1);
        User user2 = manager.read(user1.getId());
        assertEquals(user1, user2);*/
    }

}
