package com.highsenbergs.ecofriendly.ui.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.highsenbergs.ecofriendly.R;
import com.highsenbergs.ecofriendly.ui.App;
import com.highsenbergs.ecofriendly.ui.fragments.CouponsFragment;
import com.highsenbergs.ecofriendly.ui.fragments.HomeFragment.HomeFragment;
import com.highsenbergs.ecofriendly.ui.fragments.SocialFragment.SocialFragment;

import javax.inject.Inject;

import retrofit2.Retrofit;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;

public class MainActivity extends AppCompatActivity {

    @Inject
    Retrofit retrofit;

    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        if (!checkPermission()) {
            requestPermission();
        }

        changeStatusBarColor();
        App.getApp().getDataComponent().inject( this );
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener( navigationItemSelectedListener );
        openFragment( new HomeFragment() );
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
    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor( getColor( R.color.colorTitle ));
    }

}
