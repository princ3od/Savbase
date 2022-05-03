package models;

import java.util.Date;

public class SavingAccount {
    private String name;
    private int id;
    private String nationalId;
    private String savingAccountType;
    private double surplus;
    private Date openDate;
    private Date closeDate;
    private boolean status;

    public SavingAccount(String name, int id, String nationalId, String savingAccountType, double surplus, Date openDate, Date closeDate, boolean status) {
        this.name = name;
        this.id = id;
        this.nationalId = nationalId;
        this.savingAccountType = savingAccountType;
        this.surplus = surplus;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.status = status;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public boolean isStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getSavingAccountType() {
        return savingAccountType;
    }

    public double getSurplus() {
        return surplus;
    }
}
