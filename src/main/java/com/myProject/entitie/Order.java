package com.myProject.entitie;

import com.myProject.dto.Report;
import com.myProject.dto.ReportItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable, Comparable<Order> {
    private long id;
    private User user;
    private Date date;
    private double totalAmount;

    public Order() {
    }

    public Order(long id, User user, Date date, double totalAmount) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {
        private long id;
        private User user;
        private Date date;
        private double totalAmount;

        public OrderBuilder() {
        }

        public OrderBuilder id(long id) {
            this.id = id;
            return this;
        }
        public OrderBuilder user(User user) {
            this.user = user;
            return this;
        }
        public OrderBuilder date(Date date) {
            this.date = date;
            return this;
        }
        public OrderBuilder totalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Order build() {
            return new Order(id, user, date, totalAmount);
        }
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Double.compare(order.totalAmount, totalAmount) == 0 &&
                user.equals(order.user) &&
                date.equals(order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, date, totalAmount);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", date=" + date +
                ", totalAmount=" + totalAmount +
                '}';
    }

    @Override
    public int compareTo(Order o) {
        return Long.compare(id, o.getId());
    }
}
