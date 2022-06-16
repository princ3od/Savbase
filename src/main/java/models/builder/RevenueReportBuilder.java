package models.builder;

import models.RevenueReport;

import java.sql.Date;

public class RevenueReportBuilder {
    private Date date;
    private String savingType;
    private double totalRevenue;
    private double totalCost;
    private double difference;

    public RevenueReportBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public RevenueReportBuilder setSavingType(String type) {
        this.savingType = type;
        return this;
    }

    public RevenueReportBuilder setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
        return this;
    }

    public RevenueReportBuilder setTotalCost(double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public RevenueReportBuilder setDifference(double difference) {
        this.difference = difference;
        return this;
    }

    public RevenueReport getResult() {
        return new RevenueReport(date, savingType, totalRevenue, totalCost, difference);
    }
}
