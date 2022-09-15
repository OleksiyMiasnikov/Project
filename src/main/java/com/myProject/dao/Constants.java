package com.myProject.dao;

public class Constants {
    public static final String SELECT_USER = "SELECT `users`.`id`, `login`, `password`, `email`, `roles_id`, `name` as role_name FROM `users` JOIN `roles` ON `users`.`roles_id` = `roles`.`id` WHERE `login`= ?";
    public static final String SELECT_ALL_USERS = "SELECT `users`.`id`, `login`, `password`, `email`, `roles_id`, `name` as role_name FROM `users` JOIN `roles` ON `users`.`roles_id` = `roles`.`id`";
    public static final String SELECT_ALL_GOODS = "SELECT `id`, `name`, `price`, `unit` FROM `goods`";
    public static final String SELECT_ALL_ROLES  = "SELECT `id`, `name` FROM `roles`";
    public static final String INSERT_USER = "INSERT INTO users VALUES (default, ?, ?, ?, ?)";
    public static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    public static final String UPDATE_USER = "UPDATE users SET login = ?, password = ?, email = ?, roles_id = ? WHERE id = ?";
    public static final String GET_ROLE_ID = "SELECT `id` FROM `roles` WHERE `name` = ?";
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
