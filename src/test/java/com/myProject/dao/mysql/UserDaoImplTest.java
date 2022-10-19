package com.myProject.dao.mysql;

import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.myProject.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserDaoImplTest {
    private UserDaoImpl userDao;
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
        userDao = (UserDaoImpl) DaoFactoryImpl.getInstance().getUserDao();
        con.createStatement().executeUpdate(CREATE_ROLE_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_ROLE_TABLE);
        con.createStatement().executeUpdate(CREATE_USER_TABLE);
    }

    @AfterEach
    void tearDown() throws SQLException {
        con.createStatement().executeUpdate(DROP_USER_TABLE);
        con.createStatement().executeUpdate(DROP_ROLE_TABLE);
    }

    @Test
    void userCreateAndCheckEqualityTest() throws SQLException {
        User user = User.builder()
                        .id(0L)
                        .login("Ivanov")
                        .password("")
                        .email("i@i")
                        .role(Role.builder()
                                .id(1)
                                .name("admin")
                                .build())
                        .build();
        User user2 = userDao.create(con, user);
        assertEquals("Ivanov",  user.getLogin());
        assertEquals(user, user2, "Two users must be equaled if their logins are equaled");
    }

    @Test
    public void whenDuplicateLoginExceptionThrown() throws SQLException {
        User user1 = User.builder()
                        .id(0L)
                        .login("Ivanov")
                        .password("")
                        .email("i@i")
                        .role(Role.builder()
                                .id(1)
                                .name("admin")
                                .build())
                        .build();
        userDao.create(con, user1);
        User user2 = User.builder()
                        .id(0L)
                        .login("Ivanov")
                        .password("")
                        .email("2@i")
                        .role(Role.builder()
                                .id(2)
                                .name("cashier")
                                .build())
                        .build();
        Exception exception = assertThrows(SQLException.class, () -> {
            userDao.create(con, user2);
        });
    }

    @Test
    void userUpdateTest() throws SQLException {
        User user = User.builder()
                        .id(0L)
                        .login("Ivanov")
                        .password("")
                        .email("i@i")
                        .role(Role.builder()
                                .id(1)
                                .name("admin")
                                .build())
                        .build();
        user = userDao.create(con, user);
        user.setEmail("234@i");
        userDao.update(con, user);
        User user1 = userDao.read(con, user.getId());
        assertEquals("234@i",  user1.getEmail());
    }

    @Test
    void findUserByNameTest() throws SQLException {
        User user = User.builder()
                        .id(0L)
                        .login("Ivanov")
                        .password("")
                        .email("i@i")
                        .role(Role.builder()
                                .id(1)
                                .name("admin")
                                .build())
                        .build();
        user = userDao.create(con, user);
        User user1 = userDao.findByName(con, "Ivanov");
        assertEquals(user, user1, "Two users must be equaled");
    }
    @Test
    void findAllUsersTest() throws SQLException {
        List<User> userList = new ArrayList<>();
        User user1 = User.builder()
                        .id(0L)
                        .login("Ivanov")
                        .password("")
                        .email("i@i")
                        .role(Role.builder()
                                .id(1)
                                .name("admin")
                                .build())
                        .build();
        userList.add(userDao.create(con, user1));
        User user2 = User.builder()
                        .id(0L)
                        .login("Petrov")
                        .password("")
                        .email("p@p")
                        .role(Role.builder()
                                .id(2)
                                .name("cashier")
                                .build())
                        .build();
        userList.add(userDao.create(con, user2));
        assertEquals(2, userDao.findRowsTotal(con));
        assertEquals(userList, userDao.findAll(con, 0, 100));
        user1 = userList.remove(1);
        userDao.delete(con, user1.getId());
        assertEquals(userList, userDao.findAll(con, 0, 100));
    }
}
