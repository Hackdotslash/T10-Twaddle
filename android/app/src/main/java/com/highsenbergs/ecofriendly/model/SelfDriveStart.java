package com.highsenbergs.ecofriendly.model;

import com.google.gson.annotations.SerializedName;

public class SelfDriveStart {
    @SerializedName( "uuid" )
    private String uuid;
    @SerializedName( "username" )
    private String username;
    @SerializedName( "bluetooth_name" )
    private String bluetooth_name;
    @SerializedName( "bluetooth_addr" )
    private String bluetooth_addr;
    @SerializedName( "location_latitude" )
    private double location_latitude;
    @SerializedName( "location_longitude" )
    private double location_longitude;

    public SelfDriveStart(String uuid, String username, String bluetooth_name, String bluetooth_addr, double location_latitude, double location_longitude) {
        this.uuid = uuid;
        this.username = username;
        this.bluetooth_name = bluetooth_name;
        this.bluetooth_addr = bluetooth_addr;
        this.location_latitude = location_latitude;
        this.location_longitude = location_longitude;
    }

}
