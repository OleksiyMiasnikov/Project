package com.myProject.dao;

public class Constants {
    public static final String SELECT_USER = "SELECT `user`.`id`, `login`, `password`, `email`, `role_id`, `name` as role_name FROM `user` JOIN `role` ON `user`.`role_id` = `role`.`id` WHERE `login`= ?";
    public static final String SELECT_ALL_USERS = "SELECT `user`.`id`, `login`, `password`, `email`, `role_id`, `name` as role_name FROM `user` JOIN `role` ON `user`.`role_id` = `role`.`id`";
    public static final String SELECT_ALL_GOODS = "SELECT `id`, `name`, `price`, `unit` FROM `goods`";
    public static final String SELECT_ALL_IN_WAREHOUSE  = "SELECT `id`, `quantity`, `goods_id` FROM `warehouse`";
    public static final String SELECT_ALL_ROLES  = "SELECT `id`, `name` FROM `role`";
    public static final String INSERT_USER = "INSERT INTO user VALUES (default, ?, ?, ?, ?)";
    public static final String INSERT_GOODS = "INSERT INTO goods VALUES (default, ?, ?, ?)";
    public static final String READ_GOODS_BY_ID = "SELECT `id`, `name`, `price`, `unit` FROM `goods` WHERE `id` = ?";
    public static final String UPDATE_GOODS = "UPDATE goods SET name = ?, unit = ?, price = ? WHERE id = ?";
    public static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    public static final String UPDATE_USER = "UPDATE user SET login = ?, password = ?, email = ?, role_id = ? WHERE id = ?";
    public static final String GET_ROLE_ID = "SELECT `id` FROM `role` WHERE `name` = ?";
    public static final String CONTEXT_NAME = "java:comp/env/jdbc/projectConnectionPool";
    public static final String  COMMAND_SHOW_USERS = "Show users";
    public static final String  COMMAND_ADD_USER = "Add user";
    public static final String  COMMAND_UPDATE_USER = "Update user";
    public static final String  COMMAND_DELETE_USER = "Delete user";
    public static final String  COMMAND_NEW_ORDER = "New order";
    public static final String  COMMAND_REPORTS = "Reports";
    public static final String  COMMAND_NEW_GOODS = "New goods";
    public static final String  COMMAND_LIST_OF_GOODS = "List of goods";
    public static final String  COMMAND_INCOME = "Income";
}
