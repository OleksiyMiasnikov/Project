package com.myProject.dao.mysql;

import com.myProject.dao.*;
import com.myProject.dao.OrderDao;

public class DaoFactoryImpl extends DaoFactory {

    private final UserDao userDao = new UserDaoImpl();
    private final RoleDao roleDao = new RoleDaoImpl();
    private final ProductDao productDao = new ProductDaoImpl();
    private final WarehouseDao warehouseDao = new WarehouseDaoImpl();
    private final OrderDao orderDao = new OrderDaoImpl();
    private final OrderDetailsDao orderDetailsDao = new OrderDetailsDaoImpl();

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public RoleDao getRoleDao() {
        return roleDao;
    }

    @Override
    public ProductDao getProductDao() {
        return productDao;
    }

    @Override
    public WarehouseDao getWarehouseDao() {
        return warehouseDao;
    }

    @Override
    public OrderDao getOrderDao() {
        return orderDao;
    }

    @Override
    public OrderDetailsDao getOrderDetailsDao() {
        return orderDetailsDao;
    }


}
