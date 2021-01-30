package com.highsenbergs.ecofriendly.ui.fragments.SocialFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.highsenbergs.ecofriendly.R;

public class FriendsFragment extends Fragment {

    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_friends , container , false );
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        LinearLayout dynamicFriends = view.findViewById( R.id.dynamic_friends );
        View friend1 = getLayoutInflater().inflate( R.layout.card_friends_score , dynamicFriends, false );
        TextView tv_name = friend1.findViewById( R.id.friend_name);
        tv_name.setText( "Pranoppal" );
        TextView tv_username = friend1.findViewById( R.id.friend_username);
        tv_username.setText( "@pranoppal" );
        dynamicFriends.addView(friend1);

        View friend2 = getLayoutInflater().inflate( R.layout.card_friends_score , dynamicFriends, false );
        TextView tv_name2 = friend2.findViewById( R.id.friend_name);
        tv_name2.setText( "Sayooj Bala" );
        TextView tv_username2 = friend2.findViewById( R.id.friend_username);
        tv_username2.setText( "@sayooj_bkl" );
        dynamicFriends.addView(friend2);

        View friend3 = getLayoutInflater().inflate( R.layout.card_friends_score , dynamicFriends, false );
        TextView tv_name3 = friend3.findViewById( R.id.friend_name);
        tv_name3.setText( "Siddharth Kulkarni" );
        TextView tv_username3 = friend3.findViewById( R.id.friend_username);
        tv_username3.setText( "@sidk" );
        dynamicFriends.addView(friend3);
    }


}
