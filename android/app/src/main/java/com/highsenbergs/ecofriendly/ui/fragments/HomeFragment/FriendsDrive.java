package com.highsenbergs.ecofriendly.ui.fragments.HomeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.highsenbergs.ecofriendly.R;
import com.highsenbergs.ecofriendly.services.LocationTrack;

public class FriendsDrive extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     view = inflater.inflate( R.layout.fragment_drive_friends , container , false );
     return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        Button btn_location = view.findViewById( R.id.btn_location );
        btn_location.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationTrack locationTrack = new LocationTrack( getActivity() );
            }
        } );
    }
}
