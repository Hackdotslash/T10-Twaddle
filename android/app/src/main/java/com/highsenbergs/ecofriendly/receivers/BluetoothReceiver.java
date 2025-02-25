package com.highsenbergs.ecofriendly.receivers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public  class BluetoothReceiver extends BroadcastReceiver {
    private BluetoothReceiverListener receiverListener = null;

    public BluetoothReceiver() {}
    public BluetoothReceiver(BluetoothReceiverListener listener) {
        this.receiverListener = listener;
    }
    public void InitialiseBluetoothListener(BluetoothReceiverListener bluetoothReceiverListener){
        this.receiverListener = bluetoothReceiverListener;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String TAG = this.getClass().getSimpleName();
        String action = intent.getAction();
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//        Log.i( TAG, "onReceive: device=" + device);
        //TODO: location must be turned on ask for permission
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            BluetoothDevice availableDevices = intent.getParcelableExtra( BluetoothDevice.EXTRA_DEVICE );
            if(receiverListener != null) {
                receiverListener.getAvailableDevices( availableDevices );
//                Log.i( TAG, "onReceive: Device found, name=" + availableDevices.getName() + " address=" + availableDevices.getAddress() );
            }
            else
                Log.i( TAG, "onReceive: no listener" );
        }
        else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
            if(receiverListener != null ) {
                Log.i( TAG, "onReceive: Device is now connected" );
                receiverListener.getConnectedDevice( device );
            }
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

    public interface BluetoothReceiverListener{
        void getAvailableDevices(BluetoothDevice bluetoothDevice);
        void getConnectedDevice(BluetoothDevice bluetoothDevice);
    }
}