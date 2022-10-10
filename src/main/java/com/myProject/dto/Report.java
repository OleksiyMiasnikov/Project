package com.myProject.dto;

import com.myProject.entitie.Unit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Report {
    private final Date startDate;
    private final Date endDate;
    private String seniorCashier;
    private final List<ReportItem> reportList;
    private final double kgTotal;
    private final int pcsTotal;
    private final double amountTotal;

    public Report(Date startDate, Date endDate, String seniorCashier, List<ReportItem> reportList) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.seniorCashier = seniorCashier;
        this.reportList = reportList;
        kgTotal = reportList.stream()
                .mapToDouble(r -> (r.getUnit().equals(Unit.KG)) ? r.getQuantity():0)
                .sum();
        pcsTotal = (int) reportList.stream()
                .mapToDouble(r -> (r.getUnit().equals(Unit.PCS)) ? r.getQuantity():0)
                .sum();
        amountTotal = reportList.stream()
                .mapToDouble(ReportItem::getAmount)
                .sum();
    }

    public static ReportBuilder builder() {
        return new Report.ReportBuilder();
    }

    public static class ReportBuilder {
        private Date startDate;
        private Date endDate;
        private String seniorCashier;
        private List<ReportItem> list;

        public ReportBuilder() {
        }

        public ReportBuilder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }
        public ReportBuilder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }
        public ReportBuilder seniorCashier(String seniorCashier) {
            this.seniorCashier = seniorCashier;
            return this;
        }
        public ReportBuilder list(List<ReportItem> list) {
            this.list = new ArrayList<>(list);
            return this;
        }

        public Report build() {
            return new Report(startDate,
                    endDate,
                    seniorCashier,
                    list);
        }
    }
    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getSeniorCashier() {
        return seniorCashier;
    }

    public void setSeniorCashier(String seniorCashier) {
        this.seniorCashier = seniorCashier;
    }

    public List<ReportItem> getReportList() {
        return reportList;
    }

    public double getKgTotal() {
        return kgTotal;
    }

    public int getPcsTotal() {
        return pcsTotal;
    }

    public double getAmountTotal() {
        return amountTotal;
    }

    @Override
    public String toString() {
        return "Report{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", seniorCashier='" + seniorCashier + '\'' +
                ", list=" + reportList +
                ", kgTotal=" + kgTotal +
                ", pcsTotal=" + pcsTotal +
                ", amountTotal=" + amountTotal +
                "}\n";
    }

    public List<String> toStrings() {
        List<String> list = new ArrayList<>();
        list.add("*** Report X ***");
        list.add("------------------------------------------------------------------------------");
        list.add("Start date: " + startDate);
        list.add("End date  : " + endDate);
        list.add("Senior cashier: " + seniorCashier);
        list.add("------------------------------------------------------------------------------");
        list.add(String.format("%-5s", "Id") +
                String.format("%-30s", "Product name") +
                String.format("%-5s", "Unit") +
                String.format("%-10s", "Quantity") +
                String.format("%-13s", "Price") +
                String.format("%-13s", "Amount"));
        list.add("------------------------------------------------------------------------------");
        for (ReportItem element : reportList) {
            list.add(String.format("%-5d", element.getProductId()) +
                    String.format("%-30s", element.getProductName()) +
                    String.format("%-5s", element.getUnit().getLabel()) +
                    String.format("% 7.2f", element.getQuantity()) +
                    String.format("% 11.2f", element.getPrice()) +
                    String.format("% 12.2f", element.getAmount()));
        }
        list.add("------------------------------------------------------------------------------");
        list.add("Total quantity (kg): " + String.format("%-7.2f", kgTotal));
        list.add("Total quantity (pcs): " + String.format("%-7d", pcsTotal));
        list.add("Total amount: " + String.format("%-9.2f", amountTotal));
        return list;
    }
}
