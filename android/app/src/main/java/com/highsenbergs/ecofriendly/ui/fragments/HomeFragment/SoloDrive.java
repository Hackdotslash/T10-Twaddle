package com.highsenbergs.ecofriendly.ui.fragments.HomeFragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.highsenbergs.ecofriendly.R;
import com.highsenbergs.ecofriendly.model.EndTripCoins;
import com.highsenbergs.ecofriendly.model.OngoingJourneyCoordinates;
import com.highsenbergs.ecofriendly.model.SelfDriveStart;
import com.highsenbergs.ecofriendly.network.RetrofitApiInterface;
import com.highsenbergs.ecofriendly.receivers.BluetoothReceiver;
import com.highsenbergs.ecofriendly.services.LocationTrack;
import com.highsenbergs.ecofriendly.ui.App;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SoloDrive extends Fragment implements BluetoothReceiver.BluetoothReceiverListener {

    @Inject
    Retrofit retrofit;

    public static ArrayList<OngoingJourneyCoordinates> ongoingJourneyCoordinatesArrayList = new ArrayList<>();
    private static final String TAG = "SoloDrive";
    private View view;
    private boolean tripStatus, tripStatus1 = false, isBluetoothConnected = false, isLocationCorrect = false;
    private LocationTrack locationTrack;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<BluetoothDevice> bluetoothDeviceArrayList = null;
    private BluetoothDevice connectedBluetoothDevice;
    //    private TextView tv_system_msg;
//    private TextView tv_start_trip_latitude;
//    private TextView tv_start_trip_longitude;
//    private TextView tv_end_trip_latitude;
//    private TextView tv_end_trip_longitutde;
    private TextView tv_trip_details,tv_trip_coins;
    private Button btn_start_trip;
    private FirebaseAuth firebaseAuth;
    private BluetoothReceiver bluetoothReceiver;
    private Context context;
    private ImageView icon_coins;

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach( context );
        bluetoothReceiver = new BluetoothReceiver( SoloDrive.this );
        IntentFilter filter = new IntentFilter();
        filter.addAction( BluetoothDevice.ACTION_ACL_CONNECTED );
        filter.addAction( BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED );
        filter.addAction( BluetoothDevice.ACTION_ACL_DISCONNECTED );
        filter.addAction( BluetoothDevice.ACTION_FOUND );
        context.registerReceiver( bluetoothReceiver, filter );
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        firebaseAuth = FirebaseAuth.getInstance();
        App.getApp().getDataComponent().inject( this );
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.disable();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_drive_solo , container , false );
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
//        tv_system_msg = view.findViewById( R.id.system_msg );
//        tv_start_trip_latitude = view.findViewById( R.id.start_trip_latitude );
//        tv_start_trip_latitude.setVisibility( View.INVISIBLE );
//        tv_start_trip_longitude = view.findViewById( R.id.start_trip_longitude );
//        tv_start_trip_longitude.setVisibility( View.INVISIBLE );
//        tv_end_trip_latitude = view.findViewById( R.id.end_trip_latitude );
//        tv_end_trip_latitude.setVisibility( View.INVISIBLE );
//        tv_end_trip_longitutde = view.findViewById( R.id.end_trip_longitutde );
//        tv_end_trip_longitutde.setVisibility( View.INVISIBLE );
        btn_start_trip = view.findViewById( R.id.btn_start_trip );
        tv_trip_details = view.findViewById( R.id.tv_trip_details );
        tv_trip_coins = view.findViewById( R.id.tv_coins );
        tv_trip_coins.setVisibility( View.INVISIBLE );
        tv_trip_details.setVisibility( View.INVISIBLE );
        icon_coins = view.findViewById( R.id.icon_coins );
        btn_start_trip.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tripStatus) {
                    searchBluetoothDevices();
                    btn_start_trip.setText( "End" );
                } else {
                    sendOngoingJourneyCoordinates();
//                    try {
//                        Thread.sleep( 1000 );
//                    } catch (InterruptedException e) {
//                    }
                }
                tripStatus1 = tripStatus;
                tripStatus = !tripStatus;
            }
        } );
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        context.unregisterReceiver( bluetoothReceiver );
    }

    private void searchBluetoothDevices() {
        if (bluetoothAdapter.isEnabled()) {
//            tv_system_msg.setVisibility( View.VISIBLE );
//            tv_system_msg.setText( "Bluetooth is on, please connect to your bluetooth device" );

        } else {
            bluetoothAdapter.enable();
//            tv_system_msg.setVisibility( View.VISIBLE );
//            tv_system_msg.setText( "Turning on bluetooth" );
        }

        bluetoothAdapter.startDiscovery();
        if (isBluetoothConnected) {
            context.startService( new Intent( getActivity(), LocationTrack.class ) );
//            tv_system_msg.setText( "Getting current location" );
            try {
                Thread.sleep( 1000 );
            } catch (InterruptedException e) {
            }
            getGPSLocation();
        }

    }

    private void getGPSLocation() {
        locationTrack = new LocationTrack();
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                Log.i( TAG, "run: " );
                if (locationTrack.getLatitude() != 0 && locationTrack.getLongitude() != 0) {
                    Log.i( TAG, "run: non 0 location" );
//                    tv_system_msg.setVisibility( View.INVISIBLE );
                    if (!tripStatus1) {
//                            tv_start_trip_latitude.setVisibility( View.VISIBLE );
//                            tv_start_trip_longitude.setVisibility( View.VISIBLE );
//                            tv_start_trip_latitude.setText( "Start Trip Latitude:" + String.valueOf( locationTrack.getLatitude() ) );
//                            tv_start_trip_longitude.setText( "Start Trip:- Longitude:" + String.valueOf( locationTrack.getLongitude() ) );
                        btn_start_trip.setText( "End" );
                    } else {
//                            tv_end_trip_latitude.setVisibility( View.VISIBLE );
//                            tv_end_trip_longitutde.setVisibility( View.VISIBLE );
//                            tv_end_trip_latitude.setText( "End Trip:- Latitude:" + String.valueOf( locationTrack.getLatitude() ) );
//                            tv_end_trip_longitutde.setText( "End Trip:- Longitude:" + String.valueOf( locationTrack.getLongitude() ) );
                        btn_start_trip.setText( "Start" );
                    }
                    if (!isLocationCorrect)
                        sendStartLocationAPI();
                } else {
                    locationTrack = new LocationTrack();
                }
                handler.postDelayed( this, 10000 );
            }

        };
        handler.postDelayed( r, 10000 );

    }


    private void sendStartLocationAPI() {
        isLocationCorrect = true;
        if (isBluetoothConnected) {
            SelfDriveStart selfDriveStartAPI = new SelfDriveStart( firebaseAuth.getCurrentUser().getUid(),
                    firebaseAuth.getCurrentUser().getEmail(), connectedBluetoothDevice.getName(), connectedBluetoothDevice.getAddress(), locationTrack.getLatitude(), locationTrack.getLongitude() );
            RetrofitApiInterface apiInterface = retrofit.create( RetrofitApiInterface.class );
            Call<SelfDriveStart> call = apiInterface.startSelfDrive( selfDriveStartAPI );
            call.enqueue( new Callback<SelfDriveStart>() {
                @Override
                public void onResponse(Call<SelfDriveStart> call, Response<SelfDriveStart> response) {
                    Toast.makeText(getActivity() , "Trip started Successfully" , Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {
                        Log.i( this.getClass().getSimpleName(), "onResponse: " + response );
                    }
                }

                @Override
                public void onFailure(Call<SelfDriveStart> call, Throwable t) {
                    Log.i( TAG, "onFailure: " + t );
                }
            } );
        }
    }

    private void sendEndLocationAPI() {
        SelfDriveStart selfDriveStartAPI = new SelfDriveStart( firebaseAuth.getCurrentUser().getUid(),
                firebaseAuth.getCurrentUser().getEmail(), connectedBluetoothDevice.getName(), connectedBluetoothDevice.getAddress(), locationTrack.getLatitude(), locationTrack.getLongitude() );
        RetrofitApiInterface apiInterface = retrofit.create( RetrofitApiInterface.class );
        Call<SelfDriveStart> call = apiInterface.endSelfDrive( selfDriveStartAPI );
        call.enqueue( new Callback<SelfDriveStart>() {
            @Override
            public void onResponse(Call<SelfDriveStart> call, Response<SelfDriveStart> response) {
                Toast.makeText(getActivity() , "Trip ended Successfully" , Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    Log.i( this.getClass().getSimpleName(), "onResponse: " + response );
                }
            }

            @Override
            public void onFailure(Call<SelfDriveStart> call, Throwable t) {
                Log.i( TAG, "onFailure: " + t );
            }
        } );
    }


    private void sendOngoingJourneyCoordinates() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String JSONObject = gson.toJsonTree( ongoingJourneyCoordinatesArrayList ).toString();
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty( "drive_data", JSONObject );
        jsonObject1.addProperty( "uuid", firebaseAuth.getCurrentUser().getUid() );

        RetrofitApiInterface apiInterface = retrofit.create( RetrofitApiInterface.class );
        Call<EndTripCoins> call = apiInterface.sendJourneyCoordinates( jsonObject1 );
        call.enqueue( new Callback<EndTripCoins>() {
            @Override
            public void onResponse(Call<EndTripCoins> call, Response<EndTripCoins> response) {
                ongoingJourneyCoordinatesArrayList.clear();
                sendEndLocationAPI();
                btn_start_trip.setText( "Start" );
                tv_trip_details.setVisibility( View.VISIBLE );
                tv_trip_details.setText( "2.312$ coins" );
//
//                Log.i(TAG, "onResponse: " + response.body().toString());
//                tv_trip_details.setText( "CO2 : " + response.body().getAvg_carbon_footprint() + " Kg/min");
//                tv_trip_coins.setText( "Coins : " + response.body().getTotal_coins() );
//                tv_trip_coins.setVisibility( View.VISIBLE );
//                tv_trip_details.setVisibility( View.VISIBLE );
//                icon_coins.setVisibility( View.VISIBLE );
            }

            @Override
            public void onFailure(Call<EndTripCoins> call, Throwable t) {
                Log.i( TAG, "onFailure: " + t );
            }
        } );

    }


    @Override
    public void getAvailableDevices(BluetoothDevice bluetoothDevice) {
//        bluetoothDeviceArrayList.add( bluetoothDevice );
//        Log.i( this.getClass().getSimpleName(), "getAvailableDevices: arraylist=" + bluetoothDeviceArrayList );
    }

    @Override
    public void getConnectedDevice(BluetoothDevice bluetoothDevice) {
        connectedBluetoothDevice = bluetoothDevice;
        isBluetoothConnected = true;
        searchBluetoothDevices();
        Log.i( TAG, "getConnectedDevice: connected device" + connectedBluetoothDevice );
    }

}

//        final Thread thread = new Thread(r);
//        thread.start();
//        if (locationTrack.getLatitude() != 0 && locationTrack.getLongitude() != 0) {
//            if (!tripStatus1) {
//                tv_start_trip_latitude.setText( "Start Trip Latitude:" + String.valueOf( locationTrack.getLatitude() ) );
//                tv_start_trip_longitude.setText( "Start Trip:- Longitude:" + String.valueOf( locationTrack.getLongitude() ) );
//                btn_start_trip.setText( "End" );
//            } else {
//                tv_end_trip_latitude.setText( "End Trip:- Latitude:" + String.valueOf( locationTrack.getLatitude() ) );
//                tv_end_trip_longitutde.setText( "End Trip:- Longitude:" + String.valueOf( locationTrack.getLongitude() ) );
//                btn_start_trip.setText( "Start" );
//            }
//
//            if (isLocationCorrect) {
//                sendStartLocationAPI();
//            }
//        }


//search for available devices

//        final Handler handler = new Handler();
//        final Runnable r = new Runnable() {
//            public void run() {
//                if(!isBluetoothConnected) {
//                    bluetoothAdapter.startDiscovery();
//                    handler.postDelayed( this, 10000 );
//                }
//            }
//        };
//        handler.postDelayed( r, 10000 );
//        final Thread thread = new Thread(r);
//        thread.start();