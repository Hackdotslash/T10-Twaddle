package com.highsenbergs.ecofriendly.model;

import com.google.gson.annotations.SerializedName;

public class OngoingJourneyCoordinates {

    @SerializedName( "timestamp" )
    private long timestamp;
    @SerializedName( "latitude" )
    private double latitude;
    @SerializedName( "longitude" )
    private double longitude;

    public OngoingJourneyCoordinates(long timestamp, double latitude, double longitude) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
