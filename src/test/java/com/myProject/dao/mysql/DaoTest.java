package com.myProject.dao.mysql;

import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class DaoTest {

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

    private static Connection con;

    @BeforeAll
    static void globalSetUp() throws SQLException {
        System.out.println("Global setUp start");
        String url = "jdbc:mysql://localhost:3306/db_test";
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "18De1975");
        properties.put("autoReconnect", "true");
        properties.put("characterEncoding", "UTF-8");
        properties.put("useUnicode", "true");

        con = DriverManager.getConnection(url, properties);

        System.out.println("Global setUp finish");
    }

    @AfterAll
    static void globalTearDown() throws SQLException {
        con.close();
        System.out.println("Connection closed!");
    }

    private UserDaoImpl userDao;

    @BeforeEach
    void setUp() throws SQLException {
        System.out.println("setUp started");
        userDao = (UserDaoImpl) DaoFactoryImpl.getInstance().getUserDao();
        con.createStatement().executeUpdate(CREATE_ROLE_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_ROLE_TABLE);
        con.createStatement().executeUpdate(CREATE_USER_TABLE);
        System.out.println("setUp finished");
    }

    @AfterEach
    void tearDown() throws SQLException {
        con.createStatement().executeUpdate(DROP_USER_TABLE);
        con.createStatement().executeUpdate(DROP_ROLE_TABLE);
        System.out.println("dropped tables");
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

}
