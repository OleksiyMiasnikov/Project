package com.myProject.dao.entitie;

import java.io.Serializable;
import java.util.Objects;

public class Warehouse implements Serializable, Comparable<Warehouse> {
    private long id;
    private int quantity;
    private Goods goods;

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

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Override
    public int compareTo(Warehouse o) {
        return 0;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", goods=" + goods +
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
