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

public class FriendsLeaderboardFragment extends Fragment {

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
        ((TextView)friend1.findViewById(R.id.leaderboard_position)).setText("1");
        ((TextView)friend1.findViewById(R.id.friend_score)).setText("93");
        TextView tv_username = friend1.findViewById( R.id.friend_username);
        tv_username.setText( "@pranoppal" );
        dynamicFriends.addView(friend1);

        View friend2 = getLayoutInflater().inflate( R.layout.card_friends_score , dynamicFriends, false );
        TextView tv_name2 = friend2.findViewById( R.id.friend_name);
        tv_name2.setText( "Sayooj Bala" );
        ((TextView)friend2.findViewById(R.id.leaderboard_position)).setText("2");
        ((TextView)friend2.findViewById(R.id.friend_score)).setText("88");
        TextView tv_username2 = friend2.findViewById( R.id.friend_username);
        tv_username2.setText( "@sayooj_bkl" );
        dynamicFriends.addView(friend2);

        View friend3 = getLayoutInflater().inflate( R.layout.card_friends_score , dynamicFriends, false );
        TextView tv_name3 = friend3.findViewById( R.id.friend_name);
        tv_name3.setText( "Siddharth Kulkarni" );
        ((TextView)friend3.findViewById(R.id.leaderboard_position)).setText("3");
        ((TextView)friend3.findViewById(R.id.friend_score)).setText("76");
        TextView tv_username3 = friend3.findViewById( R.id.friend_username);
        tv_username3.setText( "@sidk" );
        dynamicFriends.addView(friend3);

        View friend4 = getLayoutInflater().inflate( R.layout.card_friends_score , dynamicFriends, false );
        ((TextView)friend4.findViewById(R.id.friend_name)).setText("Sourav Nambiar");
        ((TextView)friend4.findViewById(R.id.leaderboard_position)).setText("4");
        ((TextView)friend4.findViewById(R.id.friend_score)).setText("69");
        ((TextView)friend4.findViewById(R.id.friend_username)).setText("@souravnam");
        dynamicFriends.addView(friend4);

        View friend5 = getLayoutInflater().inflate( R.layout.card_friends_score , dynamicFriends, false );
        ((TextView)friend5.findViewById(R.id.friend_name)).setText("Himanshu Jotwani");
        ((TextView)friend5.findViewById(R.id.leaderboard_position)).setText("5");
        ((TextView)friend5.findViewById(R.id.friend_score)).setText("63");
        ((TextView)friend5.findViewById(R.id.friend_username)).setText("@hjhiman");
        dynamicFriends.addView(friend5);


        /*View friend6 = getLayoutInflater().inflate( R.layout.card_friends_score , dynamicFriends, false );
        ((TextView)friend6.findViewById(R.id.friend_name)).setText("Fahad Karim");
        ((TextView)friend6.findViewById(R.id.leaderboard_position)).setText("6");
        ((TextView)friend6.findViewById(R.id.friend_score)).setText("57");
        ((TextView)friend6.findViewById(R.id.friend_username)).setText("@fahad");
        dynamicFriends.addView(friend6);*/
    }


}
