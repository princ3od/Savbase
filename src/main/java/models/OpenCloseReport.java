package models;

import java.sql.Date;

public class OpenCloseReport {
    private Date date;
    private String savingType;
    private int open;
    private int close;
    private int difference;

    public OpenCloseReport(Date date, String savingType, int open, int close, int difference) {
        this.date = date;
        this.savingType = savingType;
        this.open = open;
        this.close = close;
        this.difference = difference;
    }

    public Date getDate() {
        return date;
    }

    public String getSavingType() {
        return savingType;
    }

    public int getOpen() {
        return open;
    }

    public int getClose() {
        return close;
    }

    public int getDifference() {
        return difference;
    }
}
