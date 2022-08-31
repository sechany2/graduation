package com.example.graduation.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.graduation.R;

public class Fragment_notice extends Fragment {
    public Fragment_notice(){}

    public static Fragment_notice newInstance() {
        Fragment_notice fragment = new Fragment_notice();
        return fragment;
    }
    private TextView notice1,notice2,n_as1,n_as2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_notice, container, false);
            notice1 = view.findViewById(R.id.tv_notice1);
            notice2 = view.findViewById(R.id.tv_notice2);
            n_as1 = view.findViewById(R.id.tv_notice1_as);
            n_as2= view.findViewById(R.id.tv_notice2_as);

        notice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n_as1.setVisibility(View.VISIBLE);
                n_as2.setVisibility(View.GONE);

            }
        });

        notice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n_as1.setVisibility(View.GONE);
                n_as2.setVisibility(View.VISIBLE);



            }
        });

        return  view;
    }


}