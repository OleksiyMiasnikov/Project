package com.myProject.dto;

import com.myProject.entitie.Unit;

import java.util.Date;
import java.util.Objects;

public class Report {
    private Date date;
    private long productId;
    private String productName;
    private Unit unit;
    private double quantity;
    private double price;

    public Report() {
    }

    public Report(Date date, long productId, String productName, Unit unit, double quantity, double price) {
        this.date = date;
        this.productId = productId;
        this.productName = productName;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
    }

    public static ReportBuilder builder() {
        return new ReportBuilder();
    }

    public static class ReportBuilder {
        private Date date;
        private long productId;
        private String productName;
        private Unit unit;
        private double quantity;
        private double price;

        public ReportBuilder() {
        }

        public ReportBuilder date(Date date) {
            this.date = date;
            return this;
        }

        public ReportBuilder productId(long productId) {
            this.productId = productId;
            return this;
        }
        public ReportBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }
        public ReportBuilder unit(Unit unit) {
            this.unit = unit;
            return this;
        }
        public ReportBuilder quantity(double quantity) {
            this.quantity = quantity;
            return this;
        }

        public ReportBuilder price(double price) {
            this.price = price;
            return this;
        }
        public Report build() {
            return new Report(date, productId, productName, unit, quantity, price);
        }
    }

    public Date getDate() {
        return date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return productId == report.productId && date.equals(report.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, productId);
    }

    @Override
    public String toString() {
        String formatQuantity = "% 7.0f";
        if (unit.equals(Unit.KG)) {
            formatQuantity = "% 4.3f";
        }
        return String.format("%16s", date) +
                String.format("%5d", productId) +
                String.format("%40s", productName) +
                String.format("%5s", unit) +
                String.format(formatQuantity, quantity) +
                String.format("% 5.2f", price) +
                System.lineSeparator();
    }
}
