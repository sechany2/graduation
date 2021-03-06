package com.example.graduation.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.graduation.R;

public class Fragmentsurvey1 extends Fragment {
    public Fragmentsurvey1(){ }

    public static Fragmentsurvey1 newInstance() {
        return new Fragmentsurvey1();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentsurvey1, container, false);

        FrameLayout frame =(FrameLayout)view.findViewById(R.id.sv_frame);
        Bundle bundle = new Bundle();
        if (getArguments() != null) {
            bundle.putString("allergy", getArguments().getString("allergy"));
            bundle.putString("disease", getArguments().getString("disease"));
            bundle.putString("pregnant", getArguments().getString("pregnant"));
            bundle.putString("baby", getArguments().getString("baby"));
           
        }

        Button sc_btn_health = (Button)view.findViewById(R.id.sc_btn_health);
        sc_btn_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("category","건강");
                Fragmentsurvey2 fragmentsurvey2 = new Fragmentsurvey2();
                fragmentsurvey2.setArguments(bundle);
                replaceFragment(fragmentsurvey2);
            }
        });

        Button sv_btn_diet = (Button)view.findViewById(R.id.sv_btn_diet);
        sv_btn_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("category","다이어트");
                Fragmentsurvey2 fragmentsurvey2 = new Fragmentsurvey2();
                fragmentsurvey2.setArguments(bundle);
                replaceFragment(fragmentsurvey2);
            }
        });

        Button sv_btn_ex = (Button)view.findViewById(R.id.sv_btn_ex);
        sv_btn_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("category","벌크업");
                Fragmentsurvey2 fragmentsurvey2 = new Fragmentsurvey2();
                fragmentsurvey2.setArguments(bundle);
                replaceFragment(fragmentsurvey2);
            }
        });

        return view;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.sv_frame, fragment).commit();
    }
}
