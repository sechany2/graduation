package com.example.graduation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


public class productActicity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_product);
        super.onCreate(savedInstanceState);
        Button btn_info = (Button)findViewById(R.id.btn_infome);
        Button btn_review = (Button)findViewById(R.id.btn_review);


        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                FragmentInfo fragmentview = new FragmentInfo();
                transaction.replace(R.id.frameProduct, fragmentview);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragmentreview fragmentview = new Fragmentreview();
                transaction.replace(R.id.frameProduct, fragmentview);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
