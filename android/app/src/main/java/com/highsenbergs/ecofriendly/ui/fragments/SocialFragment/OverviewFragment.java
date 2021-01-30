package com.highsenbergs.ecofriendly.ui.fragments.SocialFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.highsenbergs.ecofriendly.R;

public class OverviewFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_overview , container , false );
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        LinearLayout dynamicContacts = view.findViewById( R.id.ll_invite_contacts );
        View friend1 = getLayoutInflater().inflate( R.layout.card_contact_invite , dynamicContacts, false );
        TextView tv_name = friend1.findViewById( R.id.contact_name);
        tv_name.setText( "Pranoppal" );
        TextView tv_username = friend1.findViewById( R.id.contact_username);
        tv_username.setText( "@pranoppal" );
        dynamicContacts.addView(friend1);

        View friend2 = getLayoutInflater().inflate( R.layout.card_contact_invite , dynamicContacts, false );
        TextView tv_name2 = friend2.findViewById( R.id.contact_name);
        tv_name2.setText( "Bala" );
        TextView tv_username2 = friend2.findViewById( R.id.contact_username);
        tv_username2.setText( "@pranoppal" );
        dynamicContacts.addView(friend2);

        View friend3 = getLayoutInflater().inflate( R.layout.card_contact_invite , dynamicContacts, false );
        TextView tv_name3 = friend3.findViewById( R.id.contact_name);
        tv_name3.setText( "Sid" );
        TextView tv_username3 = friend3.findViewById( R.id.contact_username);
        tv_username3.setText( "@pranoppal" );
        dynamicContacts.addView(friend3);

        View friend4 = getLayoutInflater().inflate( R.layout.card_contact_invite , dynamicContacts, false );
        TextView tv_name4 = friend4.findViewById( R.id.contact_name);
        tv_name4.setText( "nambiar" );
        TextView tv_username4= friend4.findViewById( R.id.contact_username);
        tv_username4.setText( "@pranoppal" );
        dynamicContacts.addView(friend4);

        LinearLayout followingLayout = view.findViewById( R.id.ll_dynamic_following );
        View following1 = getLayoutInflater().inflate( R.layout.card_friends_following , followingLayout , false );
        TextView tv_following_name1 = following1.findViewById( R.id.following_name);
        tv_following_name1.setText( "nambiar" );
        TextView tv_following_username1= following1.findViewById( R.id.following_username);
        tv_following_username1.setText( "@pranoppal" );
        followingLayout.addView(following1);

//        View following2 = getLayoutInflater().inflate( R.layout.card_friends_following , followingLayout , false );
//        TextView tv_following_name2 = following2.findViewById( R.id.following_name);
//        tv_following_name2.setText( "Fahad" );
//        TextView tv_following_username2= following2.findViewById( R.id.following_username);
//        tv_following_username2.setText( "@pranoppal" );
//        followingLayout.addView(following2);
//
//        View following3 = getLayoutInflater().inflate( R.layout.card_friends_following , followingLayout , false );
//        TextView tv_following_name3 = following3.findViewById( R.id.following_name);
//        tv_following_name3.setText( "Jotwani" );
//        TextView tv_following_username3= following3.findViewById( R.id.following_username);
//        tv_following_username3.setText( "@pranoppal" );
//        followingLayout.addView(following3);
    }
}
