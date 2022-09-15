package com.myProject.dao.mysql;

import com.myProject.dao.DaoFactory;
import com.myProject.dao.GoodsDao;
import com.myProject.dao.RoleDao;
import com.myProject.dao.UserDao;

public class DaoFactoryImpl extends DaoFactory {

    private final UserDao userDao = new UserDaoImpl();
    private final RoleDao roleDao = new RoleDaoImpl();
    private final GoodsDao goodsDao = new GoodsDaoImpl();

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


}
