package com.myProject;

public class TestConstants {
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/db_test?user=root&password=18De1975";

    public static final String CREATE_ROLE_TABLE =
            "CREATE TABLE IF NOT EXISTS `role` (" +
                    "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "  `name` VARCHAR(45) NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)" +
                    "ENGINE = InnoDB";

    public static final String INSERT_DATA_IN_ROLE_TABLE =
            "INSERT INTO `role` (`id`, `name`)" +
                    "VALUES " +
                    "(1, 'admin')," +
                    "(2, 'cashier')," +
                    "(3, 'senior cashier')," +
                    "(4, 'commodity expert')";

    public static final String DROP_ROLE_TABLE = "DROP TABLE IF EXISTS role";

    public static final String CREATE_USER_TABLE =
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
                    "REFERENCES `db_test`.`role` (`id`) " +
                    "ON DELETE NO ACTION " +
                    "ON UPDATE NO ACTION) " +
                    "ENGINE = InnoDB";

    public static final String INSERT_DATA_IN_USER_TABLE =
            "INSERT INTO `user` (`id`, `login`, `email`, `password`, `role_id`)" +
                    "VALUES" +
                    "(1, 'admin', 'admin@admin', '21232f297a57a5a743894a0e4a801fc3', 1)," +
                    "(2, 'Alex', 'alex@alex', 'b59c67bf196a4758191e42f76670ceba', 1)," +
                    "(3, 'Bob', 'b@b', '934b535800b1cba8f96a5d72f72f1611', 2)," +
                    "(4, 'Clode', 'c@c', '2be9bd7a3434f7038ca27d1918de58bd', 3)," +
                    "(5, 'Den', 'd@d', 'dbc4d84bfcfe2284ba11beffb853a8c4', 4)";
    public static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS user";

    public static final String CREATE_ORDER_TABLE =
            "CREATE TABLE IF NOT EXISTS `order` (" +
                    "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "  `user_id` INT NOT NULL," +
                    "  `time` DATETIME NULL," +
                    "  `totalAmount` DECIMAL(15,2) NULL," +
                    "  `direction` VARCHAR(5) NOT NULL," +
                    "  `reported` DATETIME NULL," +
                    "  PRIMARY KEY (`id`, `user_id`)," +
                    "  INDEX `fk_ordes_users1_idx` (`user_id` ASC) VISIBLE," +
                    "  CONSTRAINT `fk_ordes_users1`" +
                    "    FOREIGN KEY (`user_id`)" +
                    "    REFERENCES `db_test`.`user` (`id`)" +
                    "    ON DELETE NO ACTION " +
                    "    ON UPDATE NO ACTION) " +
                    "ENGINE = InnoDB";

    public static final String INSERT_DATA_IN_ORDER_TABLE =
            "INSERT INTO `order` (`id`, `user_id`, `time`, `totalAmount`, `direction`, `reported`)" +
                    "VALUES" +
                    "( 1, 3, '2022-09-15 13:15:25', 521.5, 'OUT', 0)," +
                    "( 2, 3, '2022-09-15 14:20:25', 625, 'OUT', 0)," +
                    "( 3, 3, '2022-09-16 11:03:15', 1640.16, 'OUT', 0)," +
                    "( 4, 3, '2022-09-17 09:45:50', 10662.57, 'OUT', 0)," +
                    "( 5, 3, '2022-09-17 10:33:18', 348.63, 'OUT', 0)";

    public static final String DROP_ORDER_TABLE = "DROP TABLE IF EXISTS `order`";

    public static final String CREATE_ORDER_DETAILS_TABLE =
            "CREATE TABLE IF NOT EXISTS `order_details` (" +
                    "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "  `order_id` INT UNSIGNED NOT NULL," +
                    "  `product_id` INT UNSIGNED NOT NULL," +
                    "  `quantity` DECIMAL(10,3) UNSIGNED NULL," +
                    "  `price` DECIMAL(10,2) UNSIGNED NULL," +
                    "  PRIMARY KEY (`id`, `order_id`, `product_id`)," +
                    "  INDEX `fk_order_details_ordes1_idx` (`order_id` ASC) VISIBLE," +
                    "  INDEX `fk_order_details_goods1_idx` (`product_id` ASC) VISIBLE," +
                    "  CONSTRAINT `fk_order_details_ordes1`" +
                    "    FOREIGN KEY (`order_id`)" +
                    "    REFERENCES `db_test`.`order` (`id`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `fk_order_details_goods1`" +
                    "    FOREIGN KEY (`product_id`)" +
                    "    REFERENCES `db_test`.`product` (`id`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION) " +
                    "ENGINE = InnoDB";

    public static final String INSERT_DATA_IN_ORDER_DETAILS_TABLE =
            "INSERT INTO `order_details` (`id`, `order_id`, `product_id`, `quantity`, `price`)" +
                    "VALUES" +
                    "(01, 1, 1, 13, 15.50)," +
                    "(02, 1, 2,  2, 160.00)," +
                    "(03, 2, 3, 25, 25.00)," +
                    "(04, 3, 4,  8, 120.00)," +
                    "(05, 3, 5, 11, 35.80)," +
                    "(06, 3, 6,  3, 1023.56)," +
                    "(07, 3, 7,  2, 25.38)," +
                    "(08, 4, 8, 23, 300.0)," +
                    "(09, 4, 9, 7, 400.51)," +
                    "(10, 4,10, 1, 959.0)," +
                    "(11, 5,11, 15, 15.45)," +
                    "(12, 5,12, 1, 31.0)," +
                    "(13, 5,13, 2, 20.0)," +
                    "(14, 5,14, 1, 45.88)";
    public static final String DROP_ORDER_DETAILS_TABLE = "DROP TABLE IF EXISTS `order_details`";

    public static final String CREATE_PRODUCT_TABLE =
            "CREATE TABLE IF NOT EXISTS `product` (" +
                    "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "  `name` VARCHAR(45) NULL," +
                    "  `unit` VARCHAR(10) NOT NULL," +
                    "  `price` DECIMAL(10,2) NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)" +
                    "ENGINE = InnoDB;";

    public static final String INSERT_DATA_IN_PRODUCT_TABLE =
            "INSERT INTO `product` (`id`, `name`, `unit`, `price`)" +
                    "VALUES" +
                    "(1, 'мінеральна вода 1л','PCS', 15.50)," +
                    "(2, 'чай Greenfield','PCS', 160.00)," +
                    "(3, 'цукор','KG', 25.00)," +
                    "(4, 'сосиски', 'KG', 120.00)," +
                    "(5, 'молоко 1л', 'PCS', 35.80)," +
                    "(6,'cellphone', 'PCS', 1023.56)," +
                    "(7,'glasses', 'PCS', 25.38)," +
                    "(8,'кілька в томаті 240гр', 'PCS', 60.0)," +
                    "(9,'сир Cheddar 1.033', 'PCS', 400.51)," +
                    "(10,'Кава в зернах Lavazza', 'PCS', 959.0)," +
                    "(11,'pencil', 'PCS', 15.45)," +
                    "(12,'пиво', 'PCS', 31.0)," +
                    "(13,'сіль', 'KG', 20.0)," +
                    "(14,'олія Олейна 1,0 л', 'PCS', 60.35)," +
                    "(15,'шампунь', 'PCS', 45.88);";
    public static final String DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS `product`";

    public static final String CREATE_WAREHOUSE_TABLE =
            "CREATE TABLE IF NOT EXISTS `warehouse` (" +
                    "  `id` INT NOT NULL AUTO_INCREMENT," +
                    "  `quantity` DECIMAL(10,3) UNSIGNED NOT NULL," +
                    "  `product_id` INT UNSIGNED NOT NULL," +
                    "  PRIMARY KEY (`id`, `product_id`)," +
                    "  INDEX `fk_warehouse_goods1_idx` (`product_id` ASC) VISIBLE," +
                    "  CONSTRAINT `fk_warehouse_goods1`" +
                    "    FOREIGN KEY (`product_id`)" +
                    "    REFERENCES `db_test`.`product` (`id`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION)";

    public static final String INSERT_DATA_IN_WAREHOUSE_TABLE =
            "INSERT INTO `warehouse` (`id`, `quantity`, `product_id`)" +
                    "VALUES" +
                    "(1, 30, 1)," +
                    "(2, 5, 2)," +
                    "(3, 40, 3)," +
                    "(4, 10, 4)," +
                    "(5, 20, 5)," +
                    "(6, 30, 6)," +
                    "(7, 5, 7)," +
                    "(8, 40, 8)," +
                    "(9, 10, 9)," +
                    "(10, 20, 10)," +
                    "(11, 30, 11)," +
                    "(12, 5, 12)," +
                    "(13, 40, 13)," +
                    "(14, 10, 14);";
    public static final String DROP_WAREHOUSE_TABLE = "DROP TABLE IF EXISTS `warehouse`";

}
