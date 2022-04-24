package models;

import java.util.Date;

public class Account {
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

    public Account(int staffID, String staffName, String password, int position, String ID, String phoneNum, String address, String sex, Date birthdate, String email) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.password = password;
        this.position = position;
        this.ID = ID;
        this.phoneNum = phoneNum;
        this.address = address;
        this.sex = sex;
        this.birthdate = birthdate;
        this.email = email;
    }

    public int getStaffID() {
        return staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getPassword() {
        return password;
    }

    public int getPosition() {
        return position;
    }

    public String getID() {
        return ID;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public String getSex() {
        return sex;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getEmail() {
        return email;
    }
}

