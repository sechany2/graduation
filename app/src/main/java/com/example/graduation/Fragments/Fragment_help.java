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
    private TextView help1,help2,help3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fargment_help, container, false);

        help1 = view.findViewById(R.id.tv_help1);
        help1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        help2 = view.findViewById(R.id.tv_help2);
        help2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        help3 = view.findViewById(R.id.tv_help3);
        help3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return  view;
    }


}