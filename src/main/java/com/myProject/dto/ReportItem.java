package com.myProject.dto;

import com.myProject.entitie.Order;
import com.myProject.entitie.Unit;

import java.io.Serializable;
import java.util.Objects;

public class ReportItem implements Serializable, Comparable<Order>{
    private long productId;
    private String productName;
    private Unit unit;
    private double quantity;
    private double price;
    private double amount;

    public ReportItem() {
    }

    public ReportItem(long productId, String productName, Unit unit, double quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
        this.amount = quantity * price;
    }

    public static ReportItemBuilder builder() {
        return new ReportItemBuilder();
    }

    public static class ReportItemBuilder {

        private long productId;
        private String productName;
        private Unit unit;
        private double quantity;
        private double price;
        public ReportItemBuilder() {
        }

        public ReportItemBuilder productId(long productId) {
            this.productId = productId;
            return this;
        }

        public ReportItemBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }
        public ReportItemBuilder unit(Unit unit) {
            this.unit = unit;
            return this;
        }
        public ReportItemBuilder quantity(double quantity) {
            this.quantity = quantity;
            return this;
        }
        public ReportItemBuilder price(double price) {
            this.price = price;
            return this;
        }

        public ReportItem build() {
            return new ReportItem(productId, productName, unit, quantity, price);
        }
    }

    public long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Unit getUnit() {
        return unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportItem reportItem = (ReportItem) o;
        return productId == reportItem.productId && Double.compare(reportItem.quantity, quantity) == 0 && Double.compare(reportItem.price, price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity, price);
    }

    @Override
    public int compareTo(Order o) {
        return 0;
    }

    @Override
    public String toString() {
        return "ReportItem{" +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", unit=" + unit +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
