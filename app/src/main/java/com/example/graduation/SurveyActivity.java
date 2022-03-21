package com.example.graduation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        FrameLayout frame =(FrameLayout)findViewById(R.id.sv_frame);
        Button sv_btn_next = (Button) findViewById(R.id.sv_btn_next);
        sv_btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragmentsurvey1 fragmentsurvey1 = new Fragmentsurvey1();
                frame.removeAllViews();
                replaceFragment(fragmentsurvey1);
            }
        });



    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.sv_frame, fragment).commit();
    }
}