package com.myProject.dao.mysql;

import com.myProject.dao.*;
import com.myProject.dao.OrderDao;

public class DaoFactoryImpl extends DaoFactory {

    private final UserDao userDao = new UserDaoImpl();
    private final RoleDao roleDao = new RoleDaoImpl();
    private final GoodsDao goodsDao = new GoodsDaoImpl();
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
    public GoodsDao getGoodsDao() {
        return goodsDao;
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
