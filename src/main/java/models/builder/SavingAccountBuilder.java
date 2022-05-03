package models.builder;

import models.SavingAccount;

import java.util.Date;

public class SavingAccountBuilder {
    private String name;
    private int id;
    private String nationalId;
    private String savingAccountType;
    private double surplus;
    private Date openDate;
    private Date closeDate;
    private boolean status;

    public SavingAccountBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public SavingAccountBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SavingAccountBuilder setSavingAccountType(String type) {
        this.savingAccountType = type;
        return this;
    }

    public SavingAccountBuilder setNationalId(String id) {
        this.nationalId = id;
        return this;
    }

    public SavingAccountBuilder setSurplus(double surplus) {
        this.surplus = surplus;
        return this;
    }

    public SavingAccountBuilder setOpenDate(Date openDate) {
        this.openDate = openDate;
        return this;
    }

    public SavingAccountBuilder setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
        return this;
    }

    public SavingAccountBuilder setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public SavingAccount getResult() {
        return new SavingAccount(name, id, nationalId, savingAccountType, surplus, openDate, closeDate, status);
    }
}
