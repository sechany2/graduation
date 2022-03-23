package com.example.graduation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.slider.Slider;

public class Fragmentsurvey0 extends Fragment {
    public Fragmentsurvey0(){ }
    private Slider sl_tall,sl_age,sl_weighr;
    private TextView tv_tall,tv_weight,tv_age;
    private  int check_baby;
    private int check_pregnant;
    public static Fragmentsurvey0 newInstance() {
        return new Fragmentsurvey0();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentsurvey0, container, false);

        //Slider 슬라이더
        sl_tall=(Slider)view.findViewById(R.id.sl_tall);
        sl_age=(Slider)view.findViewById(R.id.sl_age);
        sl_weighr=(Slider)view.findViewById(R.id.sl_weight);
        sl_tall.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            @SuppressLint("RestrictedApi")
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                tv_tall.setText("키 : "+sl_tall.getValue());
            }
        });
        sl_age.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            @SuppressLint("RestrictedApi")
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                tv_age.setText("나이 : "+sl_age.getValue());
            }
        });
        sl_weighr.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            @SuppressLint("RestrictedApi")
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                tv_weight.setText("몸무게 : "+sl_weighr.getValue());
            }
        });

        //Textview
        tv_tall =(TextView)view.findViewById(R.id.tv_tall);
        tv_age =(TextView)view.findViewById(R.id.tv_age);
        tv_weight =(TextView)view.findViewById(R.id.tv_weight);

        FrameLayout frame =(FrameLayout)view.findViewById(R.id.sv_frame);

        //영유아 버튼
        Button btn_baby = (Button)view.findViewById(R.id.btn_baby);
        check_baby=0;
        btn_baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check_baby==0 ) {
                    btn_baby.setBackgroundColor(0xFFBB86FC); //puple 200
                    check_baby=1;
                }
                else{btn_baby.setBackgroundColor(0xFF6200EE); //puple 500
                    check_baby= 0 ;
                }

            }
        });

        //임산부 버튼
        Button btn_pregnant = (Button)view.findViewById(R.id.btn_pregnant);
        check_pregnant = 0;
        btn_pregnant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check_pregnant == 0 ) {
                    btn_pregnant.setBackgroundColor(0xFFBB86FC); //puple 200
                    check_pregnant = 1;
                }
                else{btn_pregnant.setBackgroundColor(0xFF6200EE); //puple 500
                    check_pregnant = 0 ;
                }



            }

        });
        //알레르기 버튼
        Button btn_allergy = (Button)view.findViewById(R.id.btn_allergy);
        btn_allergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","알레르기");
                Fragmentsurvey4 fragmentsurvey4 = new Fragmentsurvey4();
                fragmentsurvey4.setArguments(bundle);

                replaceFragment(fragmentsurvey4);
            }
        });
        //특정질환 버튼
        Button btn_disease = (Button)view.findViewById(R.id.btn_disease);
        btn_disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","특정질환");
                Fragmentsurvey4 fragmentsurvey4 = new Fragmentsurvey4();
                fragmentsurvey4.setArguments(bundle);

                replaceFragment(fragmentsurvey4);
            }
        });

        //완료 버튼
        Button sv_btn_next = (Button) view.findViewById(R.id.sv_btn_next);
        sv_btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragmentsurvey1 fragmentsurvey1 = new Fragmentsurvey1();

                replaceFragment(fragmentsurvey1);
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