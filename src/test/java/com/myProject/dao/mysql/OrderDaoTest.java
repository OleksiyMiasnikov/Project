package com.myProject.dao.mysql;

import com.myProject.entitie.Order;
import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.myProject.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderDaoTest {
    private OrderDaoImpl orderDao;
    private OrderDetailsDaoImpl orderDetailsDao;
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
        orderDao = (OrderDaoImpl) DaoFactoryImpl.getInstance().getOrderDao();
        orderDetailsDao = (OrderDetailsDaoImpl) DaoFactoryImpl.getInstance().getOrderDetailsDao();
        con.createStatement().executeUpdate(CREATE_ROLE_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_ROLE_TABLE);
        con.createStatement().executeUpdate(CREATE_USER_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_USER_TABLE);
        con.createStatement().executeUpdate(CREATE_ORDER_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_ORDER_TABLE);
        con.createStatement().executeUpdate(CREATE_ORDER_DETAILS_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_ORDER_DETAILS_TABLE);
    }

    @AfterEach
    void tearDown() throws SQLException {
        con.createStatement().executeUpdate(DROP_ROLE_TABLE);
        con.createStatement().executeUpdate(DROP_USER_TABLE);
        con.createStatement().executeUpdate(DROP_ORDER_DETAILS_TABLE);
        con.createStatement().executeUpdate(DROP_ORDER_TABLE);
    }

    @Test
    void readOrderByIdTest() throws SQLException {
        Order order = orderDao.read(con,1L);
        assertEquals(order.getUser(),
                new User(3,
                        "Bob",
                        "934b535800b1cba8f96a5d72f72f1611",
                        "b@b",
                        new Role(2, "cashier")));
        assertEquals(order.getTotalAmount(),521.5);
    }

    @Test
    void createOrderTest() throws SQLException {
        User bob = new User(3,
                "Bob",
                "934b535800b1cba8f96a5d72f72f1611",
                "b@b",
                new Role(2, "cashier"));
        Instant date = Instant.now().truncatedTo(ChronoUnit.SECONDS );
        Order order = new Order(0, bob, Date.from(date),25);
        order = orderDao.create(con, order, "OUT");
        assertEquals(order, orderDao.read(con, order.getId()));
        order.getUser().setLogin("Den");
        assertNotEquals(order, orderDao.read(con, order.getId()));
    }

}
