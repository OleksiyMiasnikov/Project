package com.myProject.dao.entitie;

import java.io.Serializable;
import java.util.Objects;

public class Warehouse implements Serializable, Comparable<Warehouse> {
    private long id;
    private int quantity;
    private Product product;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int compareTo(Warehouse o) {
        return 0;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", goods=" + product +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return id == warehouse.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
