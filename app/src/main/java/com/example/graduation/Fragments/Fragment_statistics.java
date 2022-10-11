package com.example.graduation.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.graduation.R;
import com.google.firebase.auth.FirebaseAuth;

public class Fragment_statistics extends Fragment {

    public static Fragment_statistics newInstance() {
        return new Fragment_statistics();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        String name = mAuth.getCurrentUser().getEmail();
        boolean adminCheck = name.equals("admin@a.com");

        if(adminCheck){

        }
        return view;
    }
}
