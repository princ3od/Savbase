package models.builder;

import models.OpenCloseReport;
import models.RevenueReport;

import java.sql.Date;

public class OpenCloseReportBuilder {
    private Date date;
    private String savingType;
    private int open;
    private int close;
    private int difference;

    public OpenCloseReportBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public OpenCloseReportBuilder setSavingType(String type) {
        this.savingType = type;
        return this;
    }

    public OpenCloseReportBuilder setOpen(int open) {
        this.open = open;
        return this;
    }

    public OpenCloseReportBuilder setClose(int close) {
        this.close = close;
        return this;
    }

    public OpenCloseReportBuilder setDifference(int difference) {
        this.difference = difference;
        return this;
    }

    public OpenCloseReport getResult() {
        return new OpenCloseReport(date, savingType, open, close, difference);
    }
}
