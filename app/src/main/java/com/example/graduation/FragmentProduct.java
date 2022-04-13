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
    public FragmentProduct(){ }
    private ArrayList<String> productinfo;
    private TextView tv_pdname;
    private TextView tv_pdbrandname;
    private ImageView iv_profile;
    private String pd_code;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_product, container, false);
        tv_pdname = view.findViewById(R.id.product_tv_pd_name);
        tv_pdbrandname = view.findViewById(R.id.product_tv_pd_brandname);
        iv_profile = view.findViewById(R.id.product_iv_pd_profile);

        //제품정보
        productinfo = new ArrayList<>();
        if (getArguments() != null) {
            productinfo = getArguments().getStringArrayList("product");
            tv_pdname.setText(productinfo.get(0));     //제품 이름
            tv_pdbrandname.setText(productinfo.get(1));//제품 브랜드명
            Glide.with(FragmentProduct.this).load(productinfo.get(2)).into(iv_profile); //이미지
        }
        else{
            Log.e("번들 이동 실패","오류");
        }

        //정보분석 버튼
        Button btn_info = (Button)view.findViewById(R.id.btn_infome);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //정보분석 프래그먼트로 제품정보 이동
                Bundle info = new Bundle();
                info.putStringArrayList("productinfo",productinfo);
                FragmentInfo fragmentInfo = new FragmentInfo();
                fragmentInfo.setArguments(info);
                ((MainActivity)getActivity()).replaceframeProduct(fragmentInfo);  //메인 엑티비티에 프래그먼트 이동 메소드 호출
            }
        });

        pd_code = new String();
        productinfo = getArguments().getStringArrayList("product");
        if (getArguments() != null) {
            pd_code = productinfo.get(10);
        }
        else{
            Log.e("번들 이동 실패","오류");
        }

        //리뷰 버튼
        Button btn_review = (Button)view.findViewById(R.id.btn_review);
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle rvinfo = new Bundle();
                rvinfo.putString("pd_code",pd_code);
                Fragmentreview fragmentreview = new Fragmentreview();
                fragmentreview.setArguments(rvinfo);
                ((MainActivity)getActivity()).replaceframeProduct(fragmentreview); //메인 엑티비티에 프래그먼트 이동 메소드 호출
            }
        });
        return view;
    }
}
