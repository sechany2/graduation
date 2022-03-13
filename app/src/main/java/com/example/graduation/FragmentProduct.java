package com.example.graduation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class FragmentProduct extends Fragment {
    public static FragmentProduct newInstance() {
        return new FragmentProduct();
    }
    public FragmentProduct(){

    }

    private TextView tv_pdname;
    private TextView tv_pdbrandname;
    private ImageView iv_profile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_product, container, false);


        //제품정보
        ArrayList<String> pdinfo ;
        tv_pdname = view.findViewById(R.id.product_tv_pd_name);
        tv_pdbrandname = view.findViewById(R.id.product_tv_pd_brandname);
        iv_profile = view.findViewById(R.id.product_iv_pd_profile);
        if (getArguments() != null) {
            pdinfo = getArguments().getStringArrayList("product");
            Log.e(pdinfo.get(1),pdinfo.get(2));
            tv_pdname.setText(pdinfo.get(1));
            tv_pdbrandname.setText(pdinfo.get(2));
        }
        else{
            Log.e("번들 이동 실패","화난당");
        }

        //Glide.with(FragmentProduct.this).load(pdinfo.get(3)).into(iv_profile);

        //정보분석 버튼
        Button btn_info = (Button)view.findViewById(R.id.btn_infome);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceframeProduct(Fragmentreview.newInstance()); //메인 엑티비티에 프래그먼트 이동 메소드 호출
            }
        });

        //리뷰 버튼
        Button btn_review = (Button)view.findViewById(R.id.btn_review);
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceframeProduct(FragmentInfo.newInstance()); //메인 엑티비티에 프래그먼트 이동 메소드 호출
            }
        });
        return view;
    }
}
