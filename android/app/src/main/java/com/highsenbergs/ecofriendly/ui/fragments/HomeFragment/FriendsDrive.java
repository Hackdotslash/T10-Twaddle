package com.highsenbergs.ecofriendly.ui.fragments.HomeFragment;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.highsenbergs.ecofriendly.R;
import com.highsenbergs.ecofriendly.model.CarPool;
import com.highsenbergs.ecofriendly.network.RetrofitApiInterface;
import com.highsenbergs.ecofriendly.receivers.BluetoothReceiver;
import com.highsenbergs.ecofriendly.ui.App;

import java.util.UUID;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FriendsDrive extends Fragment implements BluetoothReceiver.BluetoothReceiverListener {

    @Inject
    Retrofit retrofit;

    private static final String TAG = "FriendsDrive";
    private View view;
    private BluetoothReceiver.BluetoothReceiverListener bluetoothReceiverListener;
    private BluetoothDevice adminBluetoothDevice;
//    private ProgressBar progressBarBluetooth;
//    private TextView tv_start_trip_latitude;
//    private TextView tv_start_trip_longitude;
//    private  TextView tv_end_trip_latitude;
//    private TextView tv_end_trip_longitutde;
    private FirebaseAuth firebaseAuth;
    private BluetoothReceiver bluetoothReceiver;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach( context );
        bluetoothReceiver = new BluetoothReceiver( FriendsDrive.this );
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     view = inflater.inflate( R.layout.fragment_drive_friends , container , false );
//     bluetoothReceiverListener = this;
//     bluetoothReceiver = new BluetoothReceiver();
//     bluetoothReceiver.InitialiseBluetoothListener( bluetoothReceiverListener );
     return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        Button btn_location = view.findViewById( R.id.btn_start_trip );
//        progressBarBluetooth = view.findViewById( R.id.loading_bluetooth );
//        tv_start_trip_latitude = view.findViewById( R.id.start_trip_latitude );
//        tv_start_trip_longitude = view.findViewById( R.id.start_trip_longitude );
//        tv_end_trip_latitude = view.findViewById( R.id.end_trip_latitude);
//        tv_end_trip_longitutde = view.findViewById( R.id.end_trip_longitutde );
//
//        tv_start_trip_latitude.setVisibility( View.GONE );
//        tv_start_trip_longitude.setVisibility( View.GONE );
//        tv_end_trip_latitude.setVisibility( View.GONE );
//        tv_end_trip_longitutde.setVisibility( View.GONE );

        btn_location.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                progressBarBluetooth.setVisibility( View.VISIBLE );
                startCarPoolingAPI();
            }
        } );

        //TODO:set retrofit client

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver( bluetoothReceiver );
    }

    private void startCarPoolingAPI() {
        String uniqueID = UUID.randomUUID().toString();
        CarPool carPool = new CarPool( uniqueID ,firebaseAuth.getCurrentUser().getUid() , "78:89:90" , "89:90:90" );
        RetrofitApiInterface apiInterface = retrofit.create( RetrofitApiInterface.class );
        Call<CarPool> call = apiInterface.startCarPooling( carPool );
        call.enqueue( new Callback<CarPool>() {
            @Override
            public void onResponse(Call<CarPool> call, Response<CarPool> response) {
                if (response.isSuccessful()) {
                    Log.i( this.getClass().getSimpleName(), "onResponse: " + response );
                }
            }

            @Override
            public void onFailure(Call<CarPool> call, Throwable t) {
                Log.i( TAG, "onFailure: " + t );
            }
        } );
    }


    @Override
    public void getAvailableDevices(BluetoothDevice bluetoothDevice) {
//        if(bluetoothDevice.getAddress().equals( adminBluetoothDevice.getAddress() )){
//            progressBarBluetooth.setVisibility( View.GONE);
//            tv_start_trip_latitude.setVisibility( View.VISIBLE );
//            tv_start_trip_longitude.setVisibility( View.VISIBLE);
//            tv_end_trip_latitude.setVisibility( View.VISIBLE );
//            tv_end_trip_longitutde.setVisibility( View.VISIBLE );
//        }
    }

    @Override
    public void getConnectedDevice(BluetoothDevice bluetoothDevice) {}
}
