package com.example.graduation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class FragmentProduct extends Fragment {
    public static FragmentProduct newInstance() {
        return new FragmentProduct();
    }
    public FragmentProduct(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_product, container, false);

        Button btn_info = (Button)view.findViewById(R.id.btn_infome);
        Button btn_review = (Button)view.findViewById(R.id.btn_review);

        //정보분석 버튼
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceframeProduct(Fragmentreview.newInstance()); //메인 엑티비티에 프래그먼트 이동 메소드 호출
            }
        });

        //리뷰 버튼
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceframeProduct(FragmentInfo.newInstance()); //메인 엑티비티에 프래그먼트 이동 메소드 호출
            }
        });
        return view;
    }
}
