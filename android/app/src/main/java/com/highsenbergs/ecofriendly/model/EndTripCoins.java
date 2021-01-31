package com.highsenbergs.ecofriendly.model;

import com.google.gson.annotations.SerializedName;

public class EndTripCoins {
    @SerializedName( "message" )
    private String message;
    @SerializedName( "data" )
    private String data;
    @SerializedName( "" )
    private float avg_carbon_footprint;
    @SerializedName( "total_coins" )
    private float total_coins;

    public EndTripCoins(String message, String data, float avg_carbon_footprint, float total_coins) {
        this.message = message;
        this.data = data;
        this.avg_carbon_footprint = avg_carbon_footprint;
        this.total_coins = total_coins;
    }

    public float getAvg_carbon_footprint() {
        return avg_carbon_footprint;
    }

    public void setAvg_carbon_footprint(float avg_carbon_footprint) {
        this.avg_carbon_footprint = avg_carbon_footprint;
    }

    public float getTotal_coins() {
        return total_coins;
    }

    public void setTotal_coins(float total_coins) {
        this.total_coins = total_coins;
    }
}
