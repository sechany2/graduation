package com.example.graduation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FragmentInfo extends Fragment {
    public FragmentInfo(){

    }
    private ArrayList<String> productinfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentproduct, container, false);

        TextView tv_primary_fnclty=view.findViewById(R.id.tv_primary_fnclty);      // 3번 주된기능성
        TextView tv_ntk_mthd=view.findViewById(R.id.tv_ntk_mthd);            // 4번 섭취방법
        TextView tv_indiv_rawmtrl_nm =view.findViewById(R.id.tv_indiv_rawmtrl_nm);    // 5번 기능성원료
        TextView tv_etc_rawmtrl_nm=view.findViewById(R.id.tv_etc_rawmtrl_nm);      // 6번 기타원료
        TextView tv_cap_rawmtrl_nm=view.findViewById(R.id.tv_cap_rawmtrl_nm);      // 7번 캡슐 원료
        TextView tv_iftkn_atnt_matr_cn=view.findViewById(R.id.tv_iftkn_atnt_matr_cn);  // 8번 주의사항
        TextView tv_prdt_shap_cd_nm=view.findViewById(R.id.tv_prdt_shap_cd_nm);     // 9번 제품형태

        productinfo = new ArrayList<>();
        if (getArguments() != null) {
            productinfo = getArguments().getStringArrayList("productinfo");
            setinfo(tv_primary_fnclty,productinfo,2); // 3번 주된기능성
            setinfo(tv_ntk_mthd,productinfo,3);            // 4번 섭취방법
            setinfo(tv_indiv_rawmtrl_nm,productinfo,4);    // 5번 기능성원료
            setinfo(tv_etc_rawmtrl_nm,productinfo,5);      // 6번 기타원료
            setinfo(tv_cap_rawmtrl_nm,productinfo,6);      // 7번 캡슐 원료
            setinfo(tv_iftkn_atnt_matr_cn,productinfo,7);  // 8번 주의사항
            setinfo(tv_prdt_shap_cd_nm,productinfo,8);      // 9번 제품형태

        }
        else{
            Log.e("번들 이동 실패","오류");
        }

        return view;
    }
    public void setinfo(TextView view , ArrayList<String> arrayList,int a){
        if(arrayList.size()>a){
        view.setText(arrayList.get(a+1));
        }
        else{
            view.setText("정보를 추가해주세요");
        }
    }
}