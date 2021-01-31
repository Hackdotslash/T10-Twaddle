package com.highsenbergs.ecofriendly.ui;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.highsenbergs.ecofriendly.dagger.DaggerDataComponent;
import com.highsenbergs.ecofriendly.dagger.DataComponent;
import com.highsenbergs.ecofriendly.dagger.DataModule;
import com.highsenbergs.ecofriendly.receivers.BluetoothReceiver;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.google.GoogleEmojiProvider;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

public class App extends Application {
    private DataComponent dataComponent;
    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        EmojiManager.install(new GoogleEmojiProvider());
        dataComponent = DaggerDataComponent.builder()
                .dataModule(new DataModule(this))
                .build();
        try {
            // Google Play will install latest OpenSSL
            ProviderInstaller.installIfNeeded(getApplicationContext());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public DataComponent getDataComponent() {
        return dataComponent;
    }
    public static App getApp() {
        return app;
    }

}
