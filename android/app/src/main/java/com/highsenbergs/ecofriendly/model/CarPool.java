package com.highsenbergs.ecofriendly.model;

import com.google.gson.annotations.SerializedName;

public class CarPool {
    @SerializedName( "id" )
    private String id;
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("bluetooth_nearby")
    private String bluetooth_nearby;
    @SerializedName("bluetooth_addr")
    private String bluetooth_addr;

    public CarPool(String id , String uuid, String bluetooth_nearby, String bluetooth_addr) {
        this.id = id;
        this.uuid = uuid;
        this.bluetooth_nearby = bluetooth_nearby;
        this.bluetooth_addr = bluetooth_addr;
    }
}
