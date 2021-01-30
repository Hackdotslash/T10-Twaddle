package com.highsenbergs.ecofriendly.ui.activities;

import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.highsenbergs.ecofriendly.R;

public class UserInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user_info );
        Button btn_next = findViewById( R.id.btn_user_info_next );
        btn_next.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( UserInfoActivity.this, MainActivity.class );
                startActivity( intent );
            }
        } );
    }
}
