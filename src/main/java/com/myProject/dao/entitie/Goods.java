package com.myProject.dao.entitie;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Goods implements Serializable, Comparable<Goods>{
    private long id;
    private String name;
    private double price;
    private String unit;

    public static Goods newInstance(ResultSet resultSet) throws SQLException {
        Goods goods = new Goods();
        goods.setId(resultSet.getLong(1));
        goods.setName(resultSet.getString(2));
        goods.setPrice(resultSet.getDouble(3));
        goods.setUnit(resultSet.getString(4));
        return goods;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", unit='" + unit + '\'' +
                '}';
    }

    @Override
    public int compareTo(Goods o) {
        int result = name.compareTo(o.getName());
        return (result == 0) ? Double.compare(price, o.getPrice()) : result;
    }
}
