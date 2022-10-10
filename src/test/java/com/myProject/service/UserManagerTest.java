package com.myProject.service;

import com.myProject.dao.DaoFactory;
import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import com.myProject.service.exception.DaoException;
import org.junit.jupiter.api.*;

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
    void setUp() {
        manager = UserManager
                .getInstance(DaoFactory.getInstance().getUserDao(),
                             DaoFactory.getInstance().getRoleDao());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test() throws DaoException {
        /*User user1 = new User(0, "Petrenko", "", "p@p", new Role(1, "admin"));
        user1 = manager.addUser(user1);
        User user2 = manager.read(user1.getId());
        assertEquals(user1, user2);*/
    }

}
