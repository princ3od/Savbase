package models;

import java.sql.Date;

public class Customer {
    private int id;
    private String name;
    private String CMND;
    private String phone;
    private String email;
    private String address;
    private String sex;
    private Date birthDate;
    private boolean status;

    public Customer(int id, String name, String CMND, String phone, String email, String address, String sex, Date birthDate, boolean status) {
        this.id = id;
        this.name = name;
        this.CMND = CMND;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.sex = sex;
        this.birthDate = birthDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCMND() {
        return CMND;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getSex() {
        return sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public boolean isStatus() {
        return status;
    }
}
