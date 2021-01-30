package com.highsenbergs.ecofriendly.ui.fragments.HomeFragment;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.highsenbergs.ecofriendly.R;
import com.highsenbergs.ecofriendly.receivers.BluetoothReceiver;
import com.highsenbergs.ecofriendly.services.LocationTrack;

public class FriendsDrive extends Fragment implements BluetoothReceiver.BluetoothReceiverListener {

    private View view;
    private BluetoothReceiver.BluetoothReceiverListener bluetoothReceiverListener;
    private BluetoothReceiver bluetoothReceiver;
    private BluetoothDevice adminBluetoothDevice;
    private ProgressBar progressBarBluetooth;
    private TextView tv_start_trip_latitude;
    private TextView tv_start_trip_longitude;
    private  TextView tv_end_trip_latitude;
    private TextView tv_end_trip_longitutde;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     view = inflater.inflate( R.layout.fragment_drive_friends , container , false );
     bluetoothReceiverListener = this;
     bluetoothReceiver = new BluetoothReceiver();
     bluetoothReceiver.InitialiseBluetoothListener( bluetoothReceiverListener );
     return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        Button btn_location = view.findViewById( R.id.btn_start_trip );
        progressBarBluetooth = view.findViewById( R.id.loading_bluetooth );
        tv_start_trip_latitude = view.findViewById( R.id.start_trip_latitude );
        tv_start_trip_longitude = view.findViewById( R.id.start_trip_longitude );
        tv_end_trip_latitude = view.findViewById( R.id.end_trip_latitude);
        tv_end_trip_longitutde = view.findViewById( R.id.end_trip_longitutde );

        tv_start_trip_latitude.setVisibility( View.GONE );
        tv_start_trip_longitude.setVisibility( View.GONE );
        tv_end_trip_latitude.setVisibility( View.GONE );
        tv_end_trip_longitutde.setVisibility( View.GONE );

        btn_location.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarBluetooth.setVisibility( View.VISIBLE );
            }
        } );

        //TODO:set retrofit client

    }

    @Override
    public void getAvailableDevices(BluetoothDevice bluetoothDevice) {
        if(bluetoothDevice.getAddress().equals( adminBluetoothDevice.getAddress() )){
            progressBarBluetooth.setVisibility( View.GONE);
            tv_start_trip_latitude.setVisibility( View.VISIBLE );
            tv_start_trip_longitude.setVisibility( View.VISIBLE);
            tv_end_trip_latitude.setVisibility( View.VISIBLE );
            tv_end_trip_longitutde.setVisibility( View.VISIBLE );
        }
    }
}
