package com.myProject.entitie;

import com.myProject.dto.Report;
import com.myProject.dto.ReportItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class OrderDetails implements Serializable, Comparable<OrderDetails> {
    private long id;
    private Order order;
    private Product product;
    private double quantity;
    private double price;

    public OrderDetails() {
    }

    public OrderDetails(long id, Order order, Product product, double quantity, double price) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
    public static OrderDetailsBuilder builder() {
        return new OrderDetailsBuilder();
    }

    public static class OrderDetailsBuilder {
        private long id;
        private Order order;
        private Product product;
        private double quantity;
        private double price;

        public OrderDetailsBuilder() {
        }

        public OrderDetailsBuilder id(long id) {
            this.id = id;
            return this;
        }

        public OrderDetailsBuilder order(Order order) {
            this.order = order;
            return this;
        }

        public OrderDetailsBuilder product(Product product) {
            this.product = product;
            return this;
        }

        public OrderDetailsBuilder quantity(double quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderDetailsBuilder price(double price) {
            this.price = price;
            return this;
        }

        public OrderDetails build() {
            return new OrderDetails(id, order, product, quantity, price);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetails that = (OrderDetails) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                ", order=" + order +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(OrderDetails o) {
        return Long.compare(id, o.getId());
    }
}
