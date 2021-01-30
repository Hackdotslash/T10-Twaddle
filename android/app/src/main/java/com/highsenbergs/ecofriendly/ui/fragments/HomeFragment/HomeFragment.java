package com.highsenbergs.ecofriendly.ui.fragments.HomeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.highsenbergs.ecofriendly.R;
import com.highsenbergs.ecofriendly.services.LocationTrack;
import com.highsenbergs.ecofriendly.ui.adapters.SectionViewPagerAdapter;

public class HomeFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_home , container , false  );
        viewPager = view.findViewById( R.id.home_viewpager );
        tabLayout = view.findViewById( R.id.home_tabs );
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );

        setupViewPagerAdapter( viewPager );
        tabLayout.setupWithViewPager( viewPager );

    }

    private void setupViewPagerAdapter(ViewPager viewPager){
        SectionViewPagerAdapter sectionViewPagerAdapter = new SectionViewPagerAdapter( getChildFragmentManager() );
        sectionViewPagerAdapter.addfragment( new SoloDrive() , "Solo Drive" );
        sectionViewPagerAdapter.addfragment( new FriendsDrive() , "Drive With Friends" );
        viewPager.setAdapter( sectionViewPagerAdapter );
    }
}
