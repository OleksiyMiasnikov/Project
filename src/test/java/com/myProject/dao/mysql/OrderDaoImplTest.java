package com.myProject.dao.mysql;

import com.myProject.dto.ReportItem;
import com.myProject.entitie.Order;
import com.myProject.entitie.Role;
import com.myProject.entitie.Unit;
import com.myProject.entitie.User;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.myProject.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderDaoImplTest {
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
        con.createStatement().executeUpdate(CREATE_PRODUCT_TABLE);
        con.createStatement().executeUpdate(INSERT_DATA_IN_PRODUCT_TABLE);
    }

    @AfterEach
    void tearDown() throws SQLException {
        con.createStatement().executeUpdate(DROP_ROLE_TABLE);
        con.createStatement().executeUpdate(DROP_USER_TABLE);
        con.createStatement().executeUpdate(DROP_ORDER_DETAILS_TABLE);
        con.createStatement().executeUpdate(DROP_ORDER_TABLE);
        con.createStatement().executeUpdate(DROP_PRODUCT_TABLE);
    }

    @Test
    void readOrderByIdTest() throws SQLException {
        Order order = orderDao.read(con,1L);
        assertEquals(order.getUser(),
                User.builder()
                        .id(3)
                        .login("Bob")
                        .password("934b535800b1cba8f96a5d72f72f1611")
                        .email("b@b")
                        .role(Role.builder()
                                    .id(2)
                                    .name("cashier")
                                    .build())
                        .build());
        assertEquals(order.getTotalAmount(),521.5);
    }

    @Test
    void readOrderByWrongIdTest() throws SQLException {
        assertNull(orderDao.read(con,-2L));
    }

    @Test
    void createAndDeleteOrderTest() throws SQLException {
        User bob = User.builder()
                .id(3)
                .login("Bob")
                .password("934b535800b1cba8f96a5d72f72f1611")
                .email("b@b")
                .role(Role.builder()
                        .id(2)
                        .name("cashier")
                        .build())
                .build();
        Instant date = Instant.now().truncatedTo(ChronoUnit.SECONDS );
        Order order = Order.builder()
                .id(0L)
                .user(bob)
                .date(Date.from(date))
                .totalAmount(25)
                .build();
        order = orderDao.create(con, order, "OUT");
        assertEquals(order, orderDao.read(con, order.getId()));
        order.getUser().setLogin("Den");
        assertNotEquals(order, orderDao.read(con, order.getId()));
        int rows = orderDao.findRowsTotal(con, "OUT");
        orderDao.delete(con, order.getId());
        assertNull(orderDao.read(con, order.getId()));
        assertEquals(rows - 1,  orderDao.findRowsTotal(con, "OUT"));
    }

    @Test
    void findAllTest() throws SQLException {
        assertEquals(orderDao.findAll(con, "OUT", 0, 1000).size(), 5);
    }

    @Test
    void totalsTest() throws SQLException {
        Map<String, Double> map = orderDao.totals(con, "OUT");
        assertEquals(map.get("total_quantity"), 5);
        assertEquals(map.get("total_amount"), 13797.86);
    }

    @Test
    void updateTotalTest() throws SQLException {
        assertEquals(orderDao.read(con, 1L).getTotalAmount(), 521.5);
        orderDetailsDao.deleteByOrderId(con, 1L);
        orderDao.updateTotal(con, 1L);
        assertEquals(orderDao.read(con, 1L).getTotalAmount(), 0);
    }

    @Test
    void createReportTest() throws SQLException {
        List<ReportItem> list = new ArrayList<>();
        list.add(new ReportItem(6, "cellphone", Unit.PCS, 3.0, 1023.56)); 
        list.add(new ReportItem(7, "glasses", Unit.PCS, 2.0, 25.38)); 
        list.add(new ReportItem(11, "pencil", Unit.PCS, 15.0, 15.45));
        list.add(new ReportItem(8, "кілька в томаті 240гр", Unit.PCS, 23.0, 300.0)); 
        list.add(new ReportItem(10, "Кава в зернах Lavazza", Unit.PCS, 1.0, 959.0)); 
        list.add(new ReportItem(1, "мінеральна вода 1л", Unit.PCS, 13.0, 15.5)); 
        list.add(new ReportItem(5, "молоко 1л", Unit.PCS, 11.0, 35.8)); 
        list.add(new ReportItem(14, "олія Олейна 1,0 л", Unit.PCS, 1.0, 45.88)); 
        list.add(new ReportItem(12, "пиво", Unit.PCS, 1.0, 31.0)); 
        list.add(new ReportItem(13, "сіль", Unit.KG, 2.0, 20.0)); 
        list.add(new ReportItem(9, "сир Cheddar 1.033", Unit.PCS, 7.0, 400.51)); 
        list.add(new ReportItem(4, "сосиски", Unit.KG, 8.0, 120.0)); 
        list.add(new ReportItem(3, "цукор", Unit.KG, 25.0, 25.0)); 
        list.add(new ReportItem(2, "чай Greenfield", Unit.PCS, 2.0, 160.0));
        assertEquals(list, orderDao.createReport(con));
    }

    @Test
    void determineDatesTest() throws SQLException {
        var dates = orderDao.determineDates(con);
        assertEquals("2022-09-15 13:15:25.0", dates[0].toString());
        assertEquals("2022-09-17 10:33:18.0", dates[1].toString());
    }

    @Test
    void closeReportedOrdersTest() throws SQLException {
        assertEquals(orderDao.findRowsTotal(con, "OUT"), 5);
        orderDao.closeReportedOrders(con);
        assertEquals(orderDao.findRowsTotal(con, "OUT"), 0);
    }

}
