package com.example.graduation.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.graduation.R;

public class Fragment_set extends Fragment {
    public Fragment_set(){}

    public static Fragment_set newInstance() {
        Fragment_set fragment = new Fragment_set();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set, container, false);


        return  view;
    }


}