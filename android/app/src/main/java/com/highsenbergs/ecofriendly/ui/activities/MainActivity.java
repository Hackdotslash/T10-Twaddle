package com.highsenbergs.ecofriendly.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.system.Os;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.highsenbergs.ecofriendly.R;
import com.highsenbergs.ecofriendly.ui.fragments.BuyFragment;
import com.highsenbergs.ecofriendly.ui.fragments.HomeFragment;
import com.highsenbergs.ecofriendly.ui.fragments.SocialFragment;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    BluetoothReceiver bluetoothReceiver;
    private static int PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener( navigationItemSelectedListener );
        openFragment( new HomeFragment() );

        bluetoothReceiver = new BluetoothReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction( BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction( BluetoothDevice.ACTION_FOUND );
        this.registerReceiver(bluetoothReceiver, filter);

        //search for available devices
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                Log.i( "runnable", "run: " );
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                bluetoothAdapter.startDiscovery();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r, 1000);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                locationTrack = new LocationTrack(MainActivity.this);
//
//
//                if (locationTrack.canGetLocation()) {
//
//
//                    double longitude = locationTrack.getLongitude();
//                    double latitude = locationTrack.getLatitude();
//
//                    Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
//                } else {
//
//                    locationTrack.showSettingsAlert();
//                }
//
//            }
//        });

    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            openFragment(new HomeFragment());
                            return true;
                        case R.id.navigation_social:
                            openFragment(new SocialFragment());
                            return true;
                        case R.id.navigation_buy:
                            openFragment(new BuyFragment());
                            return true;
                    }
                    return false;
                }
            };

    private int checkPermission(){
        return ContextCompat.checkSelfPermission( getApplicationContext() , ACCESS_FINE_LOCATION );
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions( this , new String[] { ACCESS_FINE_LOCATION} , PERMISSION_REQUEST_CODE );
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver( bluetoothReceiver );
//        locationTrack.stopListener();
    }

    class BluetoothReceiver extends BroadcastReceiver{
        public BluetoothReceiver() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String TAG = this.getClass().getSimpleName();
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Log.i( TAG, "onReceive: device=" + device);
            //TODO: location must be turned on ask for permission
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice availableDevices = intent.getParcelableExtra( BluetoothDevice.EXTRA_DEVICE );
                Log.i( TAG, "onReceive: Device found, name=" + availableDevices.getName() + " address=" + availableDevices.getAddress());
            }
            else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                Log.i( TAG, "onReceive: Device is now connected" );
            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.i( TAG, "onReceive: Done Searching" );
            }
            else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
                Log.i( TAG, "onReceive: Device is about to disconnect" );
            }
            else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                Log.i( TAG, "onReceive: Device has disconnected" );
            }

        }
    }
}
