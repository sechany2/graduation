package com.example.graduation.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.graduation.R;

public class Fragment_announcement extends Fragment {
    public Fragment_announcement(){}

    public static Fragment_announcement newInstance() {
        Fragment_announcement fragment = new Fragment_announcement();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_announcement, container, false);


        return  view;
    }


}