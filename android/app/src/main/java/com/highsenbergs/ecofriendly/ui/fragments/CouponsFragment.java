package com.highsenbergs.ecofriendly.ui.fragments;

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

public class CouponsFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_buy , container , false  );
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        LinearLayout layoutCoupons = (LinearLayout) view.findViewById( R.id.dynamic_coupons );
        View coupon1 = getLayoutInflater().inflate( R.layout.card_coupon , layoutCoupons , false );
        TextView tv_company_name = coupon1.findViewById( R.id.company_name );
        tv_company_name.setText( "Motorox" );
        TextView tv_company_title = coupon1.findViewById( R.id.offer_title );
        tv_company_title.setText( "Get 20% off on your next service" );
        layoutCoupons.addView(coupon1);

        View coupon2 = getLayoutInflater().inflate( R.layout.card_coupon , layoutCoupons , false );
        ((TextView)coupon2.findViewById( R.id.company_name )).setText( "Amazon" );
        ((TextView)coupon2.findViewById( R.id.offer_title )).setText( "Get upto 50% off on vehicle accessories" );
        layoutCoupons.addView(coupon2);

        View coupon3 = getLayoutInflater().inflate( R.layout.card_coupon , layoutCoupons , false );
        ((TextView)coupon3.findViewById( R.id.company_name )).setText( "Fastag" );
        ((TextView)coupon3.findViewById( R.id.offer_title )).setText( "Get 30% off on first fastag buy" );
        layoutCoupons.addView(coupon3);

        View coupon4 = getLayoutInflater().inflate( R.layout.card_coupon , layoutCoupons , false );
        ((TextView)coupon4.findViewById( R.id.company_name )).setText( "Honda" );
        ((TextView)coupon4.findViewById( R.id.offer_title )).setText( "Get first 2-wheeler servicing free" );
        layoutCoupons.addView(coupon4);

    }
}

