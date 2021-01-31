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
        ImageView tv_company_icon = coupon1.findViewById( R.id.user_icon );
        tv_company_icon.setVisibility( View.INVISIBLE );
        TextView tv_company_title = coupon1.findViewById( R.id.offer_title );
        tv_company_title.setText( "Get 20% off on your next service" );
        layoutCoupons.addView(coupon1);

//        View coupon2 = getLayoutInflater().inflate( R.layout.card_coupon , layoutCoupons , false );
//        TextView tv_company_name1 = coupon2.findViewById( R.id.company_name );
//        tv_company_name1.setText( "Amazon" );
//        ImageView tv_company_icon1 = coupon2.findViewById( R.id.user_icon );
//        tv_company_icon1.setImageResource( R.drawable.amazon );
//        TextView tv_company_title1 = coupon2.findViewById( R.id.offer_title );
//        tv_company_title1.setText( "Get 50% off on all electronics products" );
//        layoutCoupons.addView(coupon2);

    }
}

