package com.example.graduation.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.graduation.R;

public class Fragment_help extends Fragment {
    public Fragment_help(){}

    public static Fragment_help newInstance() {
        Fragment_help fragment = new Fragment_help();
        return fragment;
    }
    private TextView help1,help2,help3,h_as1,h_as2,h_as3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fargment_help, container, false);
        h_as1 =view.findViewById(R.id.tv_help1_as);
        h_as2 =view.findViewById(R.id.tv_help2_as);
        h_as3=view.findViewById(R.id.tv_help3_as);

        help1 = view.findViewById(R.id.tv_help1);
        help1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                h_as1.setVisibility(View.VISIBLE);
                h_as2.setVisibility(View.GONE);
                h_as3.setVisibility(View.GONE);
            }
        });

        help2 = view.findViewById(R.id.tv_help2);
        help2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                h_as1.setVisibility(View.GONE);
                h_as2.setVisibility(View.VISIBLE);
                h_as3.setVisibility(View.GONE);
            }
        });

        help3 = view.findViewById(R.id.tv_help3);
        help3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                h_as1.setVisibility(View.GONE);
                h_as2.setVisibility(View.GONE);
                h_as3.setVisibility(View.VISIBLE);
            }
        });
        return  view;
    }


}