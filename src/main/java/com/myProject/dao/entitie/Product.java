package com.myProject.dao.entitie;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable, Comparable<Product>{
    private long id;
    private String name;
    private double price;
    private Unit unit;

    public Product() {
    }

    public Product(long id, String name, Unit unit, double price) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.price = price;
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

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", unit='" + unit.labelUa + '\'' +
                '}';
    }

    @Override
    public int compareTo(Product o) {
        int result = name.compareToIgnoreCase(o.getName());
        return (result == 0) ? Double.compare(price, o.getPrice()) : result;
    }
}
