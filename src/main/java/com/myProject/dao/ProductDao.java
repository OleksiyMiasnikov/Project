package com.myProject.dao;

import com.myProject.dao.entitie.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ProductDao extends Dao<Long, Product>{
    int findRowsTotal(Connection con) throws SQLException;
}
