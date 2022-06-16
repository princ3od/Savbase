package models;

import java.sql.Date;

public class RevenueReport {
    private Date date;
    private String savingType;
    private double totalRevenue;
    private double totalCost;
    private double difference;

    public RevenueReport(Date date, String savingType, double totalRevenue, double totalCost, double difference) {
        this.date = date;
        this.savingType = savingType;
        this.totalRevenue = totalRevenue;
        this.totalCost = totalCost;
        this.difference = difference;
    }

    public Date getDate() {
        return date;
    }

    public String getSavingType() {
        return savingType;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getDifference() {
        return difference;
    }
}
