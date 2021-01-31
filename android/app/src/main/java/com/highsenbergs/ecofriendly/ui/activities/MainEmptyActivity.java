package com.highsenbergs.ecofriendly.ui.activities;


import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.highsenbergs.ecofriendly.R;
import com.highsenbergs.ecofriendly.utils.PrefManager;

public class MainEmptyActivity extends AppCompatActivity {

    private Intent activityIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (new PrefManager(getApplicationContext()).isFirstTimeLaunch()) {

                    activityIntent = new Intent(MainEmptyActivity.this, WelcomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                } else {
                    activityIntent = new Intent(MainEmptyActivity.this, MainActivity.class);
                }

                startActivity(activityIntent);
                finish();
            }
        }, 4000);

    }
}