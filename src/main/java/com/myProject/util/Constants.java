package com.myProject.util;

public class Constants {
    public static final String CONTEXT_NAME = "java:comp/env/jdbc/projectConnectionPool";
    // SQL UserDao constants
    public static final String SELECT_USER = "SELECT `user`.`id`, `login`, `password`, `email`, `role_id`, `name` as `role_name` FROM `user` JOIN `role` ON `user`.`role_id` = `role`.`id` WHERE `login`= ?";
    public static final String SELECT_ALL_USERS = "SELECT `user`.`id`, `login`, `password`, `email`, `role_id`, `name` as `role_name` FROM `user` JOIN `role` ON `user`.`role_id` = `role`.`id`";
    public static final String INSERT_USER = "INSERT INTO `user` VALUES (default, ?, ?, ?, ?)";
    public static final String READ_USER_BY_ID = "SELECT `user`.`id`, `login`, `password`, `email`, `role_id`, `name` as `role_name` FROM `user` JOIN `role` ON `user`.`role_id` = `role`.`id` WHERE `user`.`id` = ?";
    public static final String DELETE_USER = "DELETE FROM `user` WHERE `id` = ?";
    public static final String UPDATE_USER = "UPDATE `user` SET `login` = ?, `password` = ?, `email` = ?, `role_id` = ? WHERE `id` = ?";

    // SQL ProductDao constants
    public static final String SELECT_ALL_PRODUCT = "SELECT `id`, `name`, `price`, `unit_name` FROM `product`";
    public static final String SELECT_ALL_PRODUCT_FROM_WAREHOUSE = "SELECT `product`.`id`,  `product`.`name`,  `product`.`price`,  `product`.`unit_name`, `warehouse`.`quantity` FROM `product` JOIN `warehouse` ON `product`.`id`=`warehouse`.`product_id`";
    public static final String INSERT_PRODUCT = "INSERT INTO product VALUES (default, ?, ?, ?)";
    public static final String READ_PRODUCT_BY_ID = "SELECT `id`, `name`, `price`, `unit_name` FROM `product` WHERE `id` = ?";
    public static final String UPDATE_PRODUCT = "UPDATE product SET name = ?, unit_name = ?, price = ? WHERE id = ?";

    // SQL WarehouseDao constants
    public static final String SELECT_ALL_IN_WAREHOUSE  = "SELECT `id`, `quantity`, `product_id` FROM `warehouse` WHERE `quantity` > 0";
    public static final String OFF_TAKE_PRODUCT  = "UPDATE `warehouse` SET `quantity` =  `quantity`- ? WHERE `product_id` = ?";
    public static final int ERROR_CODE_OUT_OF_RANGE = 1264;
    // SQL OrderDao constants
    public static final String UPDATE_TOTAL_AMOUNT_BY_ID = "UPDATE `order` SET `totalAmount` =  (SELECT SUM(`quantity`*`price`) FROM `order_details` WHERE `order_id`=? GROUP BY `order_id`) WHERE id = ?";
    public static final String SELECT_ALL_ORDERS = "SELECT `id`, `user_id`, `time`, `totalAmount` FROM `order`";
    public static final String READ_ORDER_BY_ID = "SELECT `id`, `user_id`, `time`, `totalAmount` FROM `order` WHERE `id` = ?";
    public static final String CREATE_ORDER = "INSERT INTO `order` VALUES (default, ?, ?, ?)";
    public static final String RECOVERY_QUANTITY_AFTER_DELETING_ORDER = "UPDATE `warehouse`, `order_details` SET `warehouse`.`quantity` = `warehouse`.`quantity` + `order_details`.`quantity` where `warehouse`.`product_id` = `order_details`.`product_id` and `order_details`.`order_id` = ?";
    public static final String READ_TOTAL = "SELECT `totalAmount` FROM `order` WHERE id = ?";
    public static final String DELETE_ORDER = "DELETE FROM `order` WHERE `id` = ?";

    // SQL OrderDetailsDao constants
    public static final String READ_ORDER_DETAILS_BY_ORDER_ID = "SELECT `id`, `order_id`, `product_id`, `quantity`, `price` FROM `order_details` WHERE `order_id` = ?";
    public static final String CREATE_ORDER_DETAILS = "INSERT INTO `order_details` VALUES (default, ?, ?, ?, ?)";
    public static final String DELETE_ORDER_DETAILS_BY_ORDER_ID = "DELETE FROM `order_details` WHERE `order_id` = ?";

    // SQL RoleDao constants
    public static final String GET_ROLE_ID = "SELECT `id` FROM `role` WHERE `name` = ?";
    public static final String SELECT_ALL_ROLES  = "SELECT `id`, `name` FROM `role`";


    // command constants
    public static final String COMMAND_SHOW_USERS = "Show users";
    public static final String COMMAND_ADD_USER = "Add user";
    public static final String COMMAND_UPDATE_USER = "Update user";
    public static final String COMMAND_CREATE_DATABASE = "Create database";
    public static final String COMMAND_DELETE_USER = "Delete user";
    public static final String COMMAND_DELETE_ORDER = "Delete order";
    public static final String COMMAND_DELETE_ORDER_PRODUCT = "Delete ordered product";
    public static final String COMMAND_NEW_ORDER = "New order";
    public static final String COMMAND_REPORTS = "Reports";
    public static final String COMMAND_NEW_PRODUCT = "New product";
    public static final String COMMAND_LIST_OF_PRODUCT = "List of product";
    public static final String COMMAND_INCOME = "Income";
    public static final String COMMAND_REMAINS = "Remains";
    public static final String COMMAND_ORDERS = "Orders";
}
