package models.builder;

import models.Customer;
import models.SavingAccount;

import java.sql.Date;

public class CustomerBuilder {
    private int id;
    private String name;
    private String CMND;
    private String phone;
    private String email;
    private String address;
    private String sex;
    private Date birthDate;
    private boolean status;

    public CustomerBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public CustomerBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public CustomerBuilder setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public CustomerBuilder setCMND(String CMND) {
        this.CMND = CMND;
        return this;
    }

    public CustomerBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public CustomerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CustomerBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public CustomerBuilder setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public CustomerBuilder setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public Customer getResult() {
        return new Customer(id, name, CMND, phone, email, address, sex, birthDate, status);
    }
}
