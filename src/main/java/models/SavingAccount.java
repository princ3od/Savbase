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
    private String email;
    private String sex;
    private String phoneNumber;
    private String address;

    public String getEmail() {
        return email;
    }

    public String getSex() {
        return sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public SavingAccount(String name, int id, String nationalId, String savingAccountType, double surplus, Date openDate, Date closeDate, boolean status, String email, String sex, String phoneNumber, String address) {
        this.name = name;
        this.id = id;
        this.nationalId = nationalId;
        this.savingAccountType = savingAccountType;
        this.surplus = surplus;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.status = status;
        this.email = email;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.address = address;
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
