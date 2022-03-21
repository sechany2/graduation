package com.example.graduation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragmentsurvey2 extends Fragment {
    public Fragmentsurvey2(){ }

    public static Fragmentsurvey2 newInstance() {
        return new Fragmentsurvey2();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentsurvey2, container, false);
    }
}
