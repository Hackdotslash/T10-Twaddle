package com.highsenbergs.ecofriendly.model;

import com.google.gson.annotations.SerializedName;

public class UserDetails {
    @SerializedName( "username" )
    private String UUID;
    @SerializedName( "mail" )
    private String mail;
    @SerializedName( "car_model" )
    private int car_model;
    @SerializedName( "name" )
    private String name;
    @SerializedName( "age" )
    private int age;
    @SerializedName( "year" )
    private int year_manufacture;
    @SerializedName( "fuel_type" )
    private int fuel_type;

    public UserDetails(String UUID, String mail, int car_model, String name, int age, int year_manufacture, int fuel_type) {
        this.UUID = UUID;
        this.mail = mail;
        this.car_model = car_model;
        this.name = name;
        this.age = age;
        this.year_manufacture = year_manufacture;
        this.fuel_type = fuel_type;
    }
}
