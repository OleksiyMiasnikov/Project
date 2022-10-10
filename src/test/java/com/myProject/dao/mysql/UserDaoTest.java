package com.myProject.dao.mysql;

import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private UserDaoImpl userDao;
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
        User user = new User(0, "Ivanov", "", "i@i", new Role(1, "admin"));
        User user2 = userDao.create(con, user);
        assertEquals("Ivanov",  user.getLogin());
        assertEquals(user, user2, "Two users must be equaled if their logins are equaled");
    }

    @Test
    public void whenDuplicateLoginExceptionThrown() throws SQLException {
        User user1 = new User(0, "Ivanov", "", "i@i", new Role(1, "admin"));
        userDao.create(con, user1);
        User user2 = new User(0, "Ivanov", "", "2@i", new Role(2, "cashier"));
        Exception exception = assertThrows(SQLException.class, () -> {
            userDao.create(con, user2);
        });
    }

    @Test
    void userUpdateTest() throws SQLException {
        User user = new User(0, "Ivanov", "", "i@i", new Role(1, "admin"));
        user = userDao.create(con, user);
        user.setEmail("234@i");
        userDao.update(con, user);
        User user1 = userDao.read(con, user.getId());
        assertEquals("234@i",  user1.getEmail());
    }

    @Test
    void findUserByNameTest() throws SQLException {
        User user = new User(0, "Ivanov", "", "i@i", new Role(1, "admin"));
        user = userDao.create(con, user);
        User user1 = userDao.findByName(con, "Ivanov");
        assertEquals(user, user1, "Two users must be equaled");
    }
    @Test
    void findAllUsersTest() throws SQLException {
        List<User> userList = new ArrayList<>();
        User user1 = new User(0, "Ivanov", "", "i@i", new Role(1, "admin"));
        userList.add(userDao.create(con, user1));
        User user2 = new User(0, "Petrov", "", "p@p", new Role(2, "cashier"));
        userList.add(userDao.create(con, user2));
        assertEquals(2, userDao.findRowsTotal(con));
        assertEquals(userList, userDao.findAll(con, 0, 100));
        user1 = userList.remove(1);
        userDao.delete(con, user1.getId());
        assertEquals(userList, userDao.findAll(con, 0, 100));
    }
}
