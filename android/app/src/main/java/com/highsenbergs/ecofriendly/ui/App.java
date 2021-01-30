package com.highsenbergs.ecofriendly.ui;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;

import com.highsenbergs.ecofriendly.dagger.DaggerDataComponent;
import com.highsenbergs.ecofriendly.dagger.DataComponent;
import com.highsenbergs.ecofriendly.dagger.DataModule;
import com.highsenbergs.ecofriendly.receivers.BluetoothReceiver;

public class App extends Application {
    private DataComponent dataComponent;
    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        dataComponent = DaggerDataComponent.builder()
                .dataModule(new DataModule(this))
                .build();
        BluetoothReceiver bluetoothReceiver = new BluetoothReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction( BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction( BluetoothDevice.ACTION_FOUND );
        this.registerReceiver( bluetoothReceiver, filter);
    }

    public DataComponent getDataComponent() {
        return dataComponent;
    }
    public static App getApp() {
        return app;
    }

}
