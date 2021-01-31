package com.highsenbergs.ecofriendly.services;

import android.Manifest;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.TimeUnit;

public class LocationTrack extends Service {

    private static final String TAG = "LocationTrack";
    private static double latitude ;
    private static double longitude ;
    private double accuracy;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
//    private LocationListener locationListener;

    public LocationTrack() {
    }

//    public LocationTrack(LocationListener locationListener) {
//        this.locationListener = locationListener;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i( TAG, "onCreate" );
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient( this );
        createLocationCallback();
        createLocationRequest();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i( TAG, "onStartCommand" );
        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            stopSelf();
            return START_NOT_STICKY;
        }

        getLastKnownLocation();
        startPeriodicLocationUpdate();

        return START_STICKY;
    }

//    public LocationTrack(Context context) {
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient( context );
//        locationManager = (LocationManager) context.getSystemService( LOCATION_SERVICE );
//        //TODO: improve getlocation
//            Log.i( TAG, "LocationTrack: " );
//            LocationRequest locationRequest = LocationRequest.create();
//            locationRequest.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
//            locationRequest.setInterval( 1000 );
//
//            LocationCallback locationCallback = new LocationCallback() {
//                @Override
//                public void onLocationResult(LocationResult locationResult) {
//                    super.onLocationResult( locationResult );
//                    latitude = locationResult.getLastLocation().getLatitude();
//                    longitude = locationResult.getLastLocation().getLongitude();
//                    accuracy = locationResult.getLastLocation().getAccuracy();
//                    Log.i( this.getClass().getSimpleName(), "onLocationChanged: " + latitude + longitude + accuracy );
//                }
//
//                @Override
//                public void onLocationAvailability(LocationAvailability locationAvailability) {
//                    super.onLocationAvailability( locationAvailability );
//                }
//            };
////            locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, this );
//            fusedLocationProviderClient.requestLocationUpdates( locationRequest, locationCallback, Looper.getMainLooper() );
//        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
//        locationTask.addOnSuccessListener( new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                Log.i( TAG, "onSuccess: location" + location.getLatitude() + location.getLongitude());
//            }
//        } );
//        }
//

//    @Override
//    public void onLocationChanged(Location location) {
//        latitude = location.getLatitude();
//        longitude = location.getLongitude();
//        accuracy = location.getAccuracy();
//        Log.i( this.getClass().getSimpleName() , "onLocationChanged: " + latitude + longitude +  accuracy );
//    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener( new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location lastLocation = task.getResult();
//                            locationListener.getLastKnownLocation( lastLocation );
                            Log.i( TAG, "getLastKnownLocation: " + lastLocation );
                        }
                    }
                } );
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest()
                .setInterval( TimeUnit.SECONDS.toMillis( 10 ) )
                .setFastestInterval( TimeUnit.SECONDS.toMillis( 10 ) )
                .setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
    }

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult( locationResult );
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    Log.i( TAG, "onLocationResult: location=" + location );
                }

                Log.i( TAG, "locationResult start" );
                for (Location location1 : locationResult.getLocations()) {
                    Log.i( TAG, "locationResult location " + location1.getLatitude() + " " + location1.getLongitude() + " " + location1.getAccuracy() + " " + location1.getProvider() );
                    latitude = location1.getLatitude();
                    longitude = location1.getLongitude();
                    Log.i( TAG, "onLocationResult: latitude inside for"+ latitude );
                }
                Log.i( TAG, "locationResult stop" );
            }
        };
    }

    private void startPeriodicLocationUpdate() {
        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Log.i( TAG, "periodic update" );
        mFusedLocationClient.requestLocationUpdates( mLocationRequest, mLocationCallback, Looper.myLooper() );
    }

//    private void stopPeriodicLocationUpdate() {
//        mFusedLocationClient.removeLocationUpdates( mLocationCallback )
//                .addOnCompleteListener( task -> LOGI( TAG, "removed location updates!" ) );
//    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public double getLatitude() {
        Log.i( TAG, "getLatitude: " + latitude );
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAccuracy() {
        return accuracy;
    }

//    public interface LocationListener{
//        void getLastKnownLocation(Location location);
//        void getCurrentLocation(Location location);
//    }
}
