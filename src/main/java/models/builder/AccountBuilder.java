package models.builder;

import models.Account;

import java.util.Date;


public class AccountBuilder {

    private int staffID;
    private String staffName;
    private String password;
    private int position;
    private String ID;
    private String phoneNum;
    private String address;
    private String sex;
    private Date birthdate;
    private String email;

    public AccountBuilder setStaffID(int staffID) {
        this.staffID = staffID;
        return this;
    }

    public AccountBuilder setStaffName(String staffName) {
        this.staffName = staffName;
        return this;
    }

    public AccountBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public AccountBuilder setPosition(int position) {
        this.position = position;
        return this;
    }

    public AccountBuilder setID(String ID) {
        this.ID = ID;
        return this;
    }

    public AccountBuilder setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
        return this;
    }

    public AccountBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public AccountBuilder setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public AccountBuilder setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public AccountBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public Account getResult() {
        return new Account(staffID, staffName, password, position, ID, phoneNum, address, sex, birthdate, email);
    }
}