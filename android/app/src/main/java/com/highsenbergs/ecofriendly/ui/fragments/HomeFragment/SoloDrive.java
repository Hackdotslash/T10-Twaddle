package com.highsenbergs.ecofriendly.ui.fragments.HomeFragment;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.highsenbergs.ecofriendly.R;
import com.highsenbergs.ecofriendly.services.LocationTrack;

public class SoloDrive extends Fragment {

    private static final String TAG = "SoloDrive";
    private View view;
    private boolean tripStatus , tripStatus1 = false;
    private LocationTrack locationTrack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_drive_solo , container , false );
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        TextView tv_systemMsg = view.findViewById( R.id.system_msg );
        final TextView tv_start_trip_latitude = view.findViewById( R.id.start_trip_latitude );
        final TextView tv_start_trip_longitude = view.findViewById( R.id.start_trip_longitude );
        final TextView tv_end_trip_latitude = view.findViewById( R.id.end_trip_latitude);
        final TextView tv_end_trip_longitutde = view.findViewById( R.id.end_trip_longitutde );
        final Button btn_start_trip = view.findViewById( R.id.btn_start_trip );
        btn_start_trip.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationTrack = new LocationTrack();
                getActivity().startService( new Intent( getActivity(), LocationTrack.class ) );
                if(!tripStatus) {
                    tv_start_trip_latitude.setText( "Start Trip Latitude:" + String.valueOf( locationTrack.getLatitude() ) );
                    tv_start_trip_longitude.setText( "Start Trip:- Longitude:" +  String.valueOf( locationTrack.getLongitude() ) );
                    btn_start_trip.setText( "End Trip" );
                }
                else {
                    tv_end_trip_latitude.setText( "End Trip:- Latitude:" +  String.valueOf( locationTrack.getLatitude() ) );
                    tv_end_trip_longitutde.setText( "End Trip:- Longitude:" +  String.valueOf( locationTrack.getLongitude() ) );
                    btn_start_trip.setText( "Start Trip" );
                }
                tripStatus1 = tripStatus;
                tripStatus = !tripStatus;


                //search for available devices
                final Handler handler = new Handler();
                final Runnable r = new Runnable() {
                    public void run() {

                        locationTrack = new LocationTrack();
                        Log.i( TAG, "run: " + locationTrack.getLongitude() + locationTrack.getLatitude() );
                        if (!tripStatus1) {
                            tv_start_trip_latitude.setText( "Start Trip Latitude:" + String.valueOf( locationTrack.getLatitude() ) );
                            tv_start_trip_longitude.setText( "Start Trip:- Longitude:" + String.valueOf( locationTrack.getLongitude() ) );
                            btn_start_trip.setText( "End Trip" );
                        } else {
                            tv_end_trip_latitude.setText( "End Trip:- Latitude:" + String.valueOf( locationTrack.getLatitude() ) );
                            tv_end_trip_longitutde.setText( "End Trip:- Longitude:" + String.valueOf( locationTrack.getLongitude() ) );
                            btn_start_trip.setText( "Start Trip" );
                        }

                        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        bluetoothAdapter.startDiscovery();
                        handler.postDelayed( this, 1000 );
                    }
                };
                handler.postDelayed( r, 10000 );


            }
        } );



    }
}
