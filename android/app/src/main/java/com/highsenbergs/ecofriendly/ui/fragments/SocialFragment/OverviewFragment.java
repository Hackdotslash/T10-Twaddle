package com.highsenbergs.ecofriendly.ui.fragments.SocialFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.highsenbergs.ecofriendly.R;
import com.highsenbergs.ecofriendly.db.ContactsDBHelper;
import com.highsenbergs.ecofriendly.model.Contacts;
import com.highsenbergs.ecofriendly.ui.viewmodel.ContactsViewModel;

import java.util.ArrayList;

public class OverviewFragment extends Fragment {

    private View view;
    private ContactsDBHelper dbHelper;
    private ContactsViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Contacts> contactsArrayList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        viewModel = new ViewModelProvider( this ).get( ContactsViewModel.class );
        dbHelper = new ContactsDBHelper( getActivity() );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_overview , container , false );
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );



        swipeRefreshLayout = view.findViewById( R.id.swipeContainer );
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetContactsAsyncTask asyncTask = new GetContactsAsyncTask();
                asyncTask.execute();
            }
        } );

        GetContactsAsyncTask asyncTask = new GetContactsAsyncTask();
        asyncTask.execute();

        LinearLayout followingLayout = view.findViewById( R.id.ll_dynamic_following );
        View following1 = getLayoutInflater().inflate( R.layout.card_friends_following , followingLayout , false );
        TextView tv_following_name1 = following1.findViewById( R.id.following_name);
        tv_following_name1.setText( "nambiar" );
        TextView tv_following_username1= following1.findViewById( R.id.following_username);
        tv_following_username1.setText( "@pranoppal" );
        followingLayout.addView(following1);

    }


    private class GetContactsAsyncTask extends AsyncTask<Void, Void, Void> {

        public GetContactsAsyncTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing( true );
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute( aVoid );
            LinearLayout dynamicContacts = view.findViewById( R.id.ll_invite_contacts );
            for (Contacts contact : contactsArrayList) {
                View friend1 = getLayoutInflater().inflate( R.layout.card_contact_invite, dynamicContacts, false );
                TextView tv_name = friend1.findViewById( R.id.contact_name );
                tv_name.setText( contact.getName() );
                TextView tv_username = friend1.findViewById( R.id.contact_username );
                tv_username.setText( String.valueOf( contact.getMobile() ) );
                dynamicContacts.addView( friend1 );
            }
            swipeRefreshLayout.setRefreshing( false );
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contactsArrayList = viewModel.getContactsArrayList();
            return null;
        }
    }

}
