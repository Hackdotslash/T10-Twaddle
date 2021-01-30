package com.highsenbergs.ecofriendly.model;

public class Contacts {
    private int id;
    private String email;
    private double mobile;
    private String name;

    public Contacts(int id, String email, double mobile, String name) {
        this.id = id;
        this.email = email;
        this.mobile = mobile;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getMobile() {
        return mobile;
    }

    public void setMobile(double mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
