package com.example.graduation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.graduation.Fragments.Fragmentsurvey0;
import com.example.graduation.R;

public class SurveyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        FrameLayout frame =(FrameLayout)findViewById(R.id.sv_frame);
        Fragmentsurvey0 fragmentsurvey0 = new Fragmentsurvey0();
        frame.removeAllViews();
        replaceFragment(fragmentsurvey0);

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.sv_frame, fragment).commit();
    }
}