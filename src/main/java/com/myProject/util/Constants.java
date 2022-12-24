package com.myProject.util;

public class Constants {
    public static final String CONTEXT_NAME = "java:comp/env/jdbc/projectConnectionPool";
    public static final String PATH = "jsp/";
    public static final String START_PAGE = "index.jsp";


    // SQL UserDao constants
    public static final String SELECT_USER = "SELECT `user`.`id`, `login`, `password`, `email`, `role_id`, `name` as `role_name` FROM `user` JOIN `role` ON `user`.`role_id` = `role`.`id` WHERE `login`= ?";
    public static final String SELECT_ALL_USERS_WITH_LIMIT =
            "SELECT `user`.`id`, `login`, `password`, `email`, `role_id`, `name` as `role_name` " +
            "FROM `user` " +
                    "JOIN `role` " +
                    "ON `user`.`role_id` = `role`.`id`  " +
            "LIMIT ?, ?";
    public static final String INSERT_USER = "INSERT INTO `user` VALUES (default, ?, ?, ?, ?)";
    public static final String READ_USER_BY_ID = "SELECT `user`.`id`, `login`, `password`, `email`, `role_id`, `name` as `role_name` FROM `user` JOIN `role` ON `user`.`role_id` = `role`.`id` WHERE `user`.`id` = ?";
    public static final String DELETE_USER = "DELETE FROM `user` WHERE `id` = ?";
    public static final String UPDATE_USER = "UPDATE `user` SET `login` = ?, `password` = ?, `email` = ?, `role_id` = ? WHERE `id` = ?";
    public static final String COUNT_ROWS_IN_USER = "SELECT count(*) AS `rows_total` FROM `user`";


    // SQL ProductDao constants
    public static final String SELECT_ALL_PRODUCT_WITH_LIMIT = "SELECT `id`, `name`, `price`, `unit` FROM `product` LIMIT ?, ?";
    public static final String SELECT_ALL_PRODUCT = "SELECT `id`, `name`, `price`, `unit` FROM `product`";
    public static final String SELECT_ALL_PRODUCT_FROM_WAREHOUSE = "SELECT `product`.`id`,  `product`.`name`,  `product`.`price`,  `product`.`unit`, `warehouse`.`quantity` FROM `product` JOIN `warehouse` ON `product`.`id`=`warehouse`.`product_id`";
    public static final String INSERT_PRODUCT = "INSERT INTO product VALUES (default, ?, ?, ?)";
    public static final String READ_PRODUCT_BY_ID = "SELECT `id`, `name`, `price`, `unit` FROM `product` WHERE `id` = ?";
    public static final String UPDATE_PRODUCT = "UPDATE `product` SET `name` = ?, `unit` = ?, `price` = ? WHERE `id` = ?";
    public static final String COUNT_ROWS_IN_PRODUCT = "SELECT count(*) AS `rows_total` FROM `product`";


    // SQL WarehouseDao constants
    public static final String SELECT_ALL_IN_WAREHOUSE_WITH_LIMIT = "SELECT `id`, `quantity`, `product_id` FROM `warehouse` WHERE `quantity` > 0 LIMIT ?, ?";
    public static final String UPDATE_QUANTITY =
            "UPDATE `warehouse` " +
            "SET `quantity` = `quantity` + ? " +
            "WHERE `product_id` = ?";
    public static final String RECOVERY_QUANTITY_AFTER_DELETING_ORDER =
            "UPDATE `warehouse`, `order_details` " +
            "SET `warehouse`.`quantity` = `warehouse`.`quantity` + `order_details`.`quantity` * ? " +
            "WHERE `warehouse`.`product_id` = `order_details`.`product_id` " +
                    "AND `order_details`.`order_id` = ?";
    public static final String COUNT_ROWS_IN_WAREHOUSE = "SELECT count(*) AS `rows_total` FROM `warehouse`";
    public static final String TOTALS_WAREHOUSE = "SELECT SUM(`quantity`) AS `total_quantity`, SUM(`price`*`quantity`) AS `total_amount` FROM `warehouse` JOIN `product` ON `product`.`id` = `warehouse`.`product_id`";
    public static final int ERROR_CODE_OUT_OF_RANGE = 1264;
    public static final String FIND_IN_WAREHOUSE_BY_PRODUCT_ID =
            "SELECT `id`, `quantity`, `product_id` " +
            "FROM `warehouse` " +
            "WHERE `product_id` = ?";
    public static final String INSERT_WAREHOUSE = "INSERT INTO `warehouse` VALUES (default, ?, ?)";



    // SQL OrderDao constants
    public static final String UPDATE_TOTAL_AMOUNT_BY_ID =
            "UPDATE `order` " +
            "SET `totalAmount` =  " +
                    "(SELECT SUM(`quantity`*`price`) " +
                    "FROM `order_details` " +
                    "WHERE `order_id`=? " +
                    "GROUP BY `order_id`) " +
            "WHERE id = ? AND `reported` = 0";
    public static final String SELECT_ALL_MOVIES_WITH_LIMIT =
            "SELECT `id`, `user_id`, `time`, `totalAmount` " +
            "FROM `order` " +
            "WHERE `direction` = ? " +
                    "AND `reported` = 0 " +
            "LIMIT ?,?";
    public static final String READ_ORDER_BY_ID = "SELECT `id`, `user_id`, `time`, `totalAmount` FROM `order` WHERE `id` = ? AND `reported` = 0";
    public static final String CREATE_ORDER = "INSERT INTO `order` VALUES (default, ?, ?, ?, ?, 0)";
    public static final String DELETE_ORDER = "DELETE FROM `order` WHERE `id` = ? AND `reported` = 0";
    public static final String COUNT_ROWS_IN_ORDER =
            "SELECT count(*) AS `rows_total` " +
            "FROM `order` " +
            "WHERE `direction` = ? " +
                    "AND `reported` = 0";
    public static final String TOTALS_ORDERS = "SELECT COUNT(*) AS `total_quantity`, SUM(`totalAmount`) AS `total_amount` FROM `order` WHERE `direction` = ? AND `reported` = 0";

    public static final String X_REPORT_ITEMS =
            "SELECT `product`.`id` AS `r_id`, " +
                    "`product`.`name` AS `r_product`, " +
                    "`product`.`unit` AS `r_unit`, " +
                    "SUM(`o_quantity`) AS `r_quantity`, " +
                    "`r_price` " +
                    "FROM `product` " +
                    "JOIN (SELECT `order`.`time`  AS `r_time`, " +
                                "`order_details`.`product_id` AS `r_product_id`, " +
                                "`order_details`.`quantity` AS `o_quantity`, " +
                                "`order_details`.`price`  AS `r_price` " +
                                "FROM `order` JOIN `order_details` ON `order`.`id` = `order_details`.`order_id` " +
                                "WHERE `order`.`direction` = 'OUT' " +
                                "AND `order`.`reported` = 0) AS `r_order` " +
                    "ON `product`.`id` = `r_order`.`r_product_id` " +
                    "GROUP BY `r_product`, `r_price` " +
                    "ORDER BY `r_product` ASC";

    public static final String X_REPORT_DATES =
            "SELECT MIN(`time`)  AS `min_date`, " +
                   "MAX(`time`)  AS `max_date` " +
            "FROM `order` " +
            "WHERE `order`.`direction` = 'OUT' " +
                    "AND `order`.`reported` = 0";
    public static final String Z_REPORT_CLOSE_ORDERS =
            "UPDATE `order` " +
                    "SET `reported` = CURRENT_TIMESTAMP()  " +
            "WHERE `order`.`direction` = 'OUT' " +
                    "AND `order`.`reported` = 0";


    // SQL OrderDetailsDao constants
    public static final String READ_ORDER_DETAILS_BY_ORDER_ID = "SELECT `id`, `order_id`, `product_id`, `quantity`, `price` FROM `order_details` WHERE `order_id` = ? LIMIT ?, ?";
    public static final String READ_ORDER_DETAILS_BY_ORDER_ID_AND_PRODUCT_ID =
            "SELECT `id`, `order_id`, `product_id`, `quantity`, `price` " +
            "FROM `order_details` " +
            "WHERE `order_id` = ? " +
                    "AND `product_id` = ?";
    public static final String UPDATE_QUANTITY_BY_ID =
            "UPDATE `order_details` " +
            "SET `quantity` =  `quantity` + ? " +
            "WHERE id = ?";
    public static final String CREATE_ORDER_DETAILS = "INSERT INTO `order_details` VALUES (default, ?, ?, ?, ?)";
    public static final String DELETE_ORDER_DETAILS = "DELETE FROM `order_details` WHERE `id` = ?";
    public static final String DELETE_ORDER_DETAILS_BY_ORDER_ID = "DELETE FROM `order_details` WHERE `order_id` = ?";
    public static final String READ_ORDER_DETAILS_BY_ID = "SELECT `id`, `order_id`, `product_id`, `quantity`, `price` FROM `order_details` WHERE `id` = ?";
    public static final String COUNT_ROWS_IN_ORDER_DETAILS = "SELECT count(*) AS `rows_total` FROM `order_details` WHERE `order_id` = ?";

    // SQL RoleDao constants
    public static final String GET_ROLE_ID = "SELECT `id` FROM `role` WHERE `name` = ?";
    public static final String SELECT_ALL_ROLES_WITH_LIMIT = "SELECT `id`, `name` FROM `role` LIMIT ?, ?";


    // command constants
    public static final String ADD_USER_COMMAND = "command.add_user";
    public static final String ADD_USER_DETAILS_COMMAND = "command.add_user_details";
    public static final String AUTHORIZATION_COMMAND = "authorization";
    public static final String BACK_COMMAND = "command.back";
    public static final String CANCEL_ORDER_COMMAND = "command.cancel_order";
    public static final String COMPLETE_ORDER_COMMAND = "command.complete_order";
    public static final String CREATE_DATABASE_COMMAND = "command.create_database";
    public static final String CREATE_PRODUCT_COMMAND = "command.create_product";
    public static final String DELETE_ORDER_COMMAND = "command.delete_order";
    public static final String DELETE_ORDER_PRODUCT_COMMAND = "command.delete_ordered_product";
    public static final String DELETE_USER_COMMAND = "command.delete_user";
    public static final String INCOMES_COMMAND = "command.incomes";
    public static final String LIST_OF_PRODUCT_COMMAND = "command.list_of_products";
    public static final String LOCALE_COMMAND = "change_locale";
    public static final String LOGOUT_COMMAND = "command.log_out";
    public static final String MOVIES_COMMAND = "command.movies";
    public static final String NEW_INCOME_COMMAND = "command.new_income";
    public static final String NEW_ORDER_COMMAND = "command.new_order";
    public static final String NEW_MOVIES_COMMAND = "command.new_movies";
    public static final String NEW_PRODUCT_COMMAND = "command.new_product";
    public static final String ORDERS_COMMAND = "command.orders";
    public static final String PREPARING_EMAIL_COMMAND = "command.preparing_email";
    public static final String REMAINS_COMMAND = "command.remains";
    public static final String SEND_EMAIL_COMMAND = "command.send_email";
    public static final String SERVE_ORDER_COMMAND = "command.serve_order";
    public static final String SHOW_USERS_COMMAND = "command.show_users";
    public static final String UPDATE_PRODUCT_COMMAND = "command.update_product";
    public static final String UPDATE_USER_COMMAND = "command.update_user";
    public static final String X_REPORT_COMMAND = "command.x_reports";
    public static final String Z_REPORT_COMMAND = "command.z_reports";
}
