package com.myProject.service;

import com.myProject.dao.DaoFactory;
import com.myProject.entitie.*;
import com.myProject.entitie.Order;
import com.myProject.service.exception.DaoException;
import com.myProject.util.ConnectionPool;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.myProject.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CashierManagerTest {

    private static CashierManager manager;
    private static UserManager userManager;
    private static CommodityExpertManager commodityExpertManager;
    @BeforeAll
    static void globalSetUp(){
        //MockedStatic<ConnectionPool> mocked = mockStatic(ConnectionPool.class);
        manager = CashierManager.getInstance(DaoFactory.getInstance().getOrderDao(),
                DaoFactory.getInstance().getOrderDetailsDao());
        userManager = UserManager.getInstance(DaoFactory.getInstance().getUserDao(),
                DaoFactory.getInstance().getRoleDao());
        commodityExpertManager =
                CommodityExpertManager.getInstance(DaoFactory.getInstance().getWarehouseDao(),
                        DaoFactory.getInstance().getProductDao());
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
        con.createStatement().executeUpdate(CREATE_ORDER_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_ORDER_TABLE);
        con.createStatement().executeUpdate(CREATE_ORDER_DETAILS_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_ORDER_DETAILS_TABLE);
        con.createStatement().executeUpdate(CREATE_PRODUCT_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_PRODUCT_TABLE);
        con.createStatement().executeUpdate(CREATE_WAREHOUSE_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_WAREHOUSE_TABLE);
        con.close();
        setConnection();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Connection con = DriverManager.getConnection(CONNECTION_URL);
        con.createStatement().executeUpdate(DROP_USER_TABLE);
        con.createStatement().executeUpdate(DROP_ROLE_TABLE);
        con.createStatement().executeUpdate(DROP_ORDER_TABLE);
        con.createStatement().executeUpdate(DROP_ORDER_DETAILS_TABLE);
        con.createStatement().executeUpdate(DROP_PRODUCT_TABLE);
        con.createStatement().executeUpdate(DROP_WAREHOUSE_TABLE);
        con.close();
    }

    private void setConnection() throws SQLException {
        ConnectionPool pool = mock(ConnectionPool.class);
        when(ConnectionPool.getInstance()).thenReturn(pool);
        when(pool.getConnection()).thenReturn(DriverManager.getConnection(CONNECTION_URL));
    }

    @Test
    void findAllTest() throws SQLException {
        assertEquals(0, manager.findAll(0, 1000, "IN").size());
        setConnection();
        assertEquals(5, manager.findAll(0, 1000, "OUT").size());
    }

    @Test
    void createOrderTest() throws SQLException {
        Instant date = Instant.now().truncatedTo(ChronoUnit.SECONDS );
        Order order = new Order(0, userManager.read(2), Date.from(date),25);
        setConnection();
        order = manager.createOrder(order, "IN");
        setConnection();
        Product product = commodityExpertManager.read(2);
        setConnection();
        Double quantityBefore = commodityExpertManager.findAll(0, 1000)
                .stream()
                .filter(p -> (p.getId() == product.getId()))
                .map(Warehouse::getQuantity)
                .reduce(0d, Double::sum);
        setConnection();
        OrderDetails orderDetails = new OrderDetails(0, order, product, 10, 25);
        orderDetails = manager.createOrderDetails(orderDetails, "IN");
        setConnection();
        Double quantityAfter = commodityExpertManager.findAll(0, 1000)
                .stream()
                .filter(p -> (p.getId() == product.getId()))
                .map(Warehouse::getQuantity)
                .reduce(0d, Double::sum);
        assertEquals(quantityAfter, quantityBefore + 10);
        setConnection();
        assertEquals(order, manager.read(order.getId()));
        setConnection();
        manager.deleteOrder(order.getId(), "IN");
        setConnection();
        Double quantityAfterDelete = commodityExpertManager.findAll(0, 1000)
                .stream()
                .filter(p -> (p.getId() == product.getId()))
                .map(Warehouse::getQuantity)
                .reduce(0d, Double::sum);
        assertEquals(quantityAfterDelete, quantityBefore);
    }

    @Test
    void findRowsTotalTest() throws DaoException {
        assertEquals(5, manager.findRowsTotal("OUT"));
    }

    @Test
    void deleteProductInOrderTest() throws SQLException {
        manager.deleteProductsInOrder("5", new String[] {"12", "13"});
        setConnection();
        assertEquals(277.63, manager.read(5).getTotalAmount());
    }
}