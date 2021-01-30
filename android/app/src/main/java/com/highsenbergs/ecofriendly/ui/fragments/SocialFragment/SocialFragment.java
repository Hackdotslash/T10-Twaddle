package com.highsenbergs.ecofriendly.ui.fragments.SocialFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.highsenbergs.ecofriendly.R;
import com.highsenbergs.ecofriendly.ui.adapters.SectionViewPagerAdapter;

public class SocialFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_social, container, false );
        viewPager = view.findViewById( R.id.social_viewpager );
        tabLayout = view.findViewById(R.id.social_tabs);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        setupViewPager( viewPager );
        tabLayout.setupWithViewPager( viewPager );
    }

    private void setupViewPager(ViewPager viewPager){
        SectionViewPagerAdapter adapter = new SectionViewPagerAdapter( getChildFragmentManager() );
        adapter.addfragment( new OverviewFragment() , "Overview" );
        adapter.addfragment( new FriendsFragment() , "You vs Friends" );
        viewPager.setAdapter( adapter );
    }
}

