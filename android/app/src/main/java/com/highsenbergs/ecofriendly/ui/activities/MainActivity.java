package com.highsenbergs.ecofriendly.ui.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.highsenbergs.ecofriendly.R;
import com.highsenbergs.ecofriendly.db.ContactsDBHelper;
import com.highsenbergs.ecofriendly.ui.fragments.CouponsFragment;
import com.highsenbergs.ecofriendly.ui.fragments.HomeFragment.HomeFragment;
import com.highsenbergs.ecofriendly.ui.fragments.SocialFragment.SocialFragment;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;
import static com.highsenbergs.ecofriendly.utils.Constants.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    BluetoothReceiver bluetoothReceiver;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private ContactsDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        if (!checkPermission()) {
            requestPermission();
        }


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
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                bluetoothAdapter.startDiscovery();
                handler.postDelayed( this, 100000 );
            }
        };
        handler.postDelayed( r, 100000 );

        dbHelper = new ContactsDBHelper( this );
        database = dbHelper.getWritableDatabase();

        getAllContacts();

    }

    private void getAllContacts(){

        ContentResolver resolver = getContentResolver();
        Cursor c = resolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.Data.HAS_PHONE_NUMBER + "!=0 AND (" + ContactsContract.Contacts.Data.MIMETYPE + "=? OR " + ContactsContract.Contacts.Data.MIMETYPE + "=?)",
                new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE},
                ContactsContract.Data.CONTACT_ID );
        c.moveToFirst();

        while (c.moveToNext()) {
            int id = c.getInt( c.getColumnIndex( ContactsContract.Data.CONTACT_ID ) );
            String name = c.getString( c.getColumnIndex( ContactsContract.Data.DISPLAY_NAME ) );
            String data1 = c.getString( c.getColumnIndex( ContactsContract.Data.DATA1 ) );
            String mail = c.getString( c.getColumnIndexOrThrow( ContactsContract.CommonDataKinds.Email.ADDRESS ) );
            ContentValues contentValues = new ContentValues();
            contentValues.put( "ID", id );
            contentValues.put( "name", name );
            contentValues.put( "phone", data1 );
            contentValues.put( "mail", mail );
            database.insert( TABLE_NAME, null, contentValues );
        }
        c.close();

    }
    private void openFragment(Fragment fragment) {
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
                            openFragment(new CouponsFragment());
                            return true;
                    }
                    return false;
                }
            };

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission( getApplicationContext(), ACCESS_FINE_LOCATION );
        int result1 = ContextCompat.checkSelfPermission( getApplicationContext(), READ_CONTACTS );
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions( this, new String[]{ACCESS_FINE_LOCATION, READ_CONTACTS}, PERMISSION_REQUEST_CODE );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean contactAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if (!locationAccepted && contactAccepted) {
                Toast.makeText( this, " Please grant permissions", Toast.LENGTH_LONG ).show();
                requestPermission();
            }
        }
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
