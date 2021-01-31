package com.highsenbergs.ecofriendly.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.highsenbergs.ecofriendly.R;
import com.highsenbergs.ecofriendly.model.UserDetails;
import com.highsenbergs.ecofriendly.network.RetrofitApiInterface;
import com.highsenbergs.ecofriendly.ui.App;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserInfoActivity extends AppCompatActivity {

    @Inject
    Retrofit retrofit;

    private static final String TAG = "UserInfoActivity";
    private int fuelType;
    private EditText et_fuel_type;
    private EditText et_name;
    private EditText et_age;
    private EditText et_year_manufacture;
    private EditText et_model;
    private FirebaseAuth firebaseAuth;
    private UserDetails userDetails;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        firebaseAuth = FirebaseAuth.getInstance();
        App.getApp().getDataComponent().inject( this );
        setContentView( R.layout.activity_user_info );
        Button btn_next = findViewById( R.id.btn_user_info_next );
         et_name = findViewById( R.id.et_name );
         et_age = findViewById( R.id.et_age );
         et_model = findViewById( R.id.et_vehicle_model );
         et_year_manufacture = findViewById( R.id.et_year_of_manufacter );

        btn_next.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDetails = new UserDetails( firebaseAuth.getCurrentUser().getUid() ,
                        firebaseAuth.getCurrentUser().getEmail(),
                        Integer.valueOf( et_model.getText().toString()),
                        et_name.getText().toString(),
                        Integer.valueOf( et_age.getText().toString() ),
                        Integer.valueOf( et_year_manufacture.getText().toString() ),
                        fuelType
                        );

                createUserDetails();
                Intent intent = new Intent( UserInfoActivity.this, MainActivity.class );
                startActivity( intent );
            }
        } );
        et_fuel_type = findViewById( R.id.et_fuel );
        et_fuel_type.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog( UserInfoActivity.this );

            }
        } );
    }

    public void showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder( context);
        builder.setTitle( "Fuel Type" );

//list of items
        final String[] items = getResources().getStringArray( R.array.array_fuel );
// set single choice items
        builder.setSingleChoiceItems( items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // item selected logic
                        fuelType = which;
                        String[] fuel_type = getResources().getStringArray( R.array.array_fuel );
                        Log.i( TAG, "onClick: " + fuel_type[fuelType] );
                        et_fuel_type.setText( fuel_type[fuelType] );
                        Log.i( TAG, "onClick: which=" + which);
                        dialog.dismiss();
                    }
                } );

        AlertDialog dialog = builder.create();
        dialog.show();
    }

        private void createUserDetails(){
            Log.i( this.getClass().getSimpleName(), "getFollowers: " );
            RetrofitApiInterface apiInterface = retrofit.create( RetrofitApiInterface.class);
            Call<UserDetails> call = apiInterface.createUser(userDetails);
            call.enqueue( new Callback<UserDetails>() {
                @Override
                public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                    if(response.isSuccessful()){
                        Log.i( this.getClass().getSimpleName(), "onResponse: " + response );
                    }
                }

                @Override
                public void onFailure(Call<UserDetails> call, Throwable t) {
                    Log.i( TAG, "onFailure: " + t );
                }
            } );
        }
}
