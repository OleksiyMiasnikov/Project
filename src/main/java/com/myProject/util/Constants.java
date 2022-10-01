package com.myProject.util;

public class Constants {
    public static final String CONTEXT_NAME = "java:comp/env/jdbc/projectConnectionPool";
    public static final String PATH = "jsp/";
    public static final String START_PAGE = "index.jsp";


    // SQL UserDao constants
    public static final String SELECT_USER = "SELECT `user`.`id`, `login`, `password`, `email`, `role_id`, `name` as `role_name` FROM `user` JOIN `role` ON `user`.`role_id` = `role`.`id` WHERE `login`= ?";
    public static final String SELECT_ALL_USERS_WITH_LIMIT = "SELECT `user`.`id`, `login`, `password`, `email`, `role_id`, `name` as `role_name` FROM `user` JOIN `role` ON `user`.`role_id` = `role`.`id`  LIMIT ?, ?";
    public static final String INSERT_USER = "INSERT INTO `user` VALUES (default, ?, ?, ?, ?)";
    public static final String READ_USER_BY_ID = "SELECT `user`.`id`, `login`, `password`, `email`, `role_id`, `name` as `role_name` FROM `user` JOIN `role` ON `user`.`role_id` = `role`.`id` WHERE `user`.`id` = ?";
    public static final String DELETE_USER = "DELETE FROM `user` WHERE `id` = ?";
    public static final String UPDATE_USER = "UPDATE `user` SET `login` = ?, `password` = ?, `email` = ?, `role_id` = ? WHERE `id` = ?";
    public static final String COUNT_ROWS_IN_USER = "SELECT count(*) AS `rows_total` FROM `user`";


    // SQL ProductDao constants
    public static final String SELECT_ALL_PRODUCT_WITH_LIMIT = "SELECT `id`, `name`, `price`, `unit_name` FROM `product` LIMIT ?, ?";
    public static final String SELECT_ALL_PRODUCT = "SELECT `id`, `name`, `price`, `unit_name` FROM `product`";
    public static final String SELECT_ALL_PRODUCT_FROM_WAREHOUSE = "SELECT `product`.`id`,  `product`.`name`,  `product`.`price`,  `product`.`unit_name`, `warehouse`.`quantity` FROM `product` JOIN `warehouse` ON `product`.`id`=`warehouse`.`product_id`";
    public static final String INSERT_PRODUCT = "INSERT INTO product VALUES (default, ?, ?, ?)";
    public static final String READ_PRODUCT_BY_ID = "SELECT `id`, `name`, `price`, `unit_name` FROM `product` WHERE `id` = ?";
    public static final String UPDATE_PRODUCT = "UPDATE product SET name = ?, unit_name = ?, price = ? WHERE id = ?";
    public static final String COUNT_ROWS_IN_PRODUCT = "SELECT count(*) AS `rows_total` FROM `product`";


    // SQL WarehouseDao constants
    public static final String SELECT_ALL_IN_WAREHOUSE_WITH_LIMIT = "SELECT `id`, `quantity`, `product_id` FROM `warehouse` WHERE `quantity` > 0 LIMIT ?, ?";
    public static final String UPDATE_QUANTITY = "UPDATE `warehouse` SET `quantity` = `quantity` + ? WHERE `product_id` = ?";
    public static final String RECOVERY_QUANTITY_AFTER_DELETING_ORDER = "UPDATE `warehouse`, `order_details` SET `warehouse`.`quantity` = `warehouse`.`quantity` + `order_details`.`quantity` WHERE `warehouse`.`product_id` = `order_details`.`product_id` AND `order_details`.`order_id` = ?";
    public static final String COUNT_ROWS_IN_WAREHOUSE = "SELECT count(*) AS `rows_total` FROM `warehouse`";
    public static final String TOTALS_WAREHOUSE = "SELECT SUM(`quantity`) AS `total_quantity`, SUM(`price`*`quantity`) AS `total_amount` FROM `warehouse` JOIN `product` ON `product`.`id` = `warehouse`.`product_id`";
    public static final int ERROR_CODE_OUT_OF_RANGE = 1264;

    // SQL OrderDao constants
    public static final String UPDATE_TOTAL_AMOUNT_BY_ID = "UPDATE `order` SET `totalAmount` =  (SELECT SUM(`quantity`*`price`) FROM `order_details` WHERE `order_id`=? GROUP BY `order_id`) WHERE id = ?";
    public static final String SELECT_ALL_ORDERS_WITH_LIMIT = "SELECT `id`, `user_id`, `time`, `totalAmount` FROM `order` WHERE `direction` = 'OUT' LIMIT ?,?";
    public static final String SELECT_ALL_INCOMES_WITH_LIMIT = "SELECT `id`, `user_id`, `time`, `totalAmount` FROM `order` WHERE `direction` = 'IN' LIMIT ?,?";
    public static final String READ_ORDER_BY_ID = "SELECT `id`, `user_id`, `time`, `totalAmount` FROM `order` WHERE `id` = ?";
    public static final String CREATE_ORDER = "INSERT INTO `order` VALUES (default, ?, ?, ?, ?)";
    public static final String CREATE_INCOME = "INSERT INTO `order` VALUES (default, ?, ?, ?, 'IN')";
    public static final String DELETE_ORDER = "DELETE FROM `order` WHERE `id` = ?";
    public static final String COUNT_ROWS_IN_ORDER = "SELECT count(*) AS `rows_total` FROM `order` WHERE `direction` = ?";
    public static final String TOTALS_ORDERS = "SELECT COUNT(*) AS `total_quantity`, SUM(`totalAmount`) AS `total_amount` FROM `order` WHERE `direction` = ?";

    // SQL OrderDetailsDao constants
    public static final String READ_ORDER_DETAILS_BY_ORDER_ID = "SELECT `id`, `order_id`, `product_id`, `quantity`, `price` FROM `order_details` WHERE `order_id` = ? LIMIT ?, ?";
    public static final String CREATE_ORDER_DETAILS = "INSERT INTO `order_details` VALUES (default, ?, ?, ?, ?)";
    public static final String DELETE_ORDER_DETAILS = "DELETE FROM `order_details` WHERE `id` = ?";
    public static final String DELETE_ORDER_DETAILS_BY_ORDER_ID = "DELETE FROM `order_details` WHERE `order_id` = ?";
    public static final String READ_ORDER_DETAILS_BY_ID = "SELECT `id`, `order_id`, `product_id`, `quantity`, `price` FROM `order_details` WHERE `id` = ?";
    public static final String COUNT_ROWS_IN_ORDER_DETAILS = "SELECT count(*) AS `rows_total` FROM `order_details` WHERE `order_id` = ?";

    // SQL RoleDao constants
    public static final String GET_ROLE_ID = "SELECT `id` FROM `role` WHERE `name` = ?";
    public static final String SELECT_ALL_ROLES_WITH_LIMIT = "SELECT `id`, `name` FROM `role` LIMIT ?, ?";


    // command constants
    public static final String SHOW_USERS_COMMAND = "Show users";
    public static final String ADD_USER_COMMAND = "Add user";
    public static final String ADD_USER_DETAILS_COMMAND = "AddUserDetails";
    public static final String UPDATE_USER_COMMAND = "Update user";
    public static final String UPDATE_PRODUCT_COMMAND = "Update product";
    public static final String CREATE_DATABASE_COMMAND = "Create database";
    public static final String DELETE_USER_COMMAND = "Delete user";
    public static final String DELETE_ORDER_COMMAND = "Delete order";
    public static final String DELETE_ORDER_PRODUCT_COMMAND = "Delete ordered product";
    public static final String NEW_ORDER_COMMAND = "New order";
    public static final String REPORTS_COMMAND = "Reports";
    public static final String NEW_PRODUCT_COMMAND = "New product";
    public static final String CREATE_PRODUCT_COMMAND = "Create product";
    public static final String LIST_OF_PRODUCT_COMMAND = "List of products";
    public static final String NEW_INCOME_COMMAND = "New income";
    public static final String INCOMES_COMMAND = "Incomes";
    public static final String REMAINS_COMMAND = "Remains";
    public static final String ORDERS_COMMAND = "Orders";
    public static final String MOVIES_COMMAND = "Movies";
    public static final String AUTHORIZATION_COMMAND = "authorization";
    public static final String LOGOUT_COMMAND = "Log out";
    public static final String SERVE_ORDER_COMMAND = "Serve order";
}
