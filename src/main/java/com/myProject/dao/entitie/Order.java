package com.myProject.dao.entitie;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable, Comparable<Order> {
    private long id;
    private User user;
    private Date date;
    private double amount;

    public Order() {
    }

    public Order(long id, User user, Date date, double amount) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }

    @Override
    public int compareTo(Order o) {
        return Long.compare(id, o.getId());
    }
}
