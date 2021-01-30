package com.highsenbergs.ecofriendly.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class LocationTrack extends Service implements LocationListener {


    private LocationManager locationManager;

    @SuppressLint("MissingPermission")
    public LocationTrack(Context context) {
        Log.i( "locationtrack", "LocationTrack: pressed" );
        locationManager = (LocationManager) context.getSystemService( LOCATION_SERVICE );
        //TODO: improve getlocation
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 12, 1000, this );
    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitutde = location.getLongitude();
        double accuracy = location.getAccuracy();
        Log.i( "LocationTrack", "onLocationChanged: latitude=" + latitude + "longitude=" + longitutde + "accuracy" + accuracy );
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
