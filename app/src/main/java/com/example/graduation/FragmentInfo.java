package com.example.graduation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FragmentInfo extends Fragment {
    public FragmentInfo() {
    }

    private ArrayList<String> productinfo;
    public ProgressBar progressBar_1, progressBar_2, progressBar_3, progressBar_4;

    private final double RIProtein = 65;  //단백질
    private final double RICarbohydrate = 130;  //Recommended intake 권장섭취량 탄수화물
    private final double RIFat = 55;  //지방
    private final double RISalt = 1.2;  //나트륨

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentproduct, container, false);

        TextView tv_primary_fnclty = view.findViewById(R.id.tv_primary_fnclty);      // 3번 주된기능성
        TextView tv_ntk_mthd = view.findViewById(R.id.tv_ntk_mthd);            // 4번 섭취방법
        TextView tv_indiv_rawmtrl_nm = view.findViewById(R.id.tv_indiv_rawmtrl_nm);    // 5번 기능성원료
        TextView tv_etc_rawmtrl_nm = view.findViewById(R.id.tv_etc_rawmtrl_nm);      // 6번 기타원료
        TextView tv_cap_rawmtrl_nm = view.findViewById(R.id.tv_cap_rawmtrl_nm);      // 7번 캡슐 원료
        TextView tv_iftkn_atnt_matr_cn = view.findViewById(R.id.tv_iftkn_atnt_matr_cn);  // 8번 주의사항
        TextView tv_prdt_shap_cd_nm = view.findViewById(R.id.tv_prdt_shap_cd_nm);     // 9번 제품형태
        TextView tv_protein = view.findViewById(R.id.tv_protein);
        TextView tv_RIProtein = view.findViewById(R.id.tv_RIProtein);
        TextView tv_carbohydrate = view.findViewById(R.id.tv_carbohydrate);
        TextView tv_RICarbohydrate = view.findViewById(R.id.tv_RICarbohydrate);
        TextView tv_fat = view.findViewById(R.id.tv_fat);
        TextView tv_RIFat = view.findViewById(R.id.tv_RIFat);
        TextView tv_salt = view.findViewById(R.id.tv_salt);
        TextView tv_RISalt = view.findViewById(R.id.tv_RISalt);


        progressBar_1 = view.findViewById(R.id.progressBar_1);       //11번 단백질
        progressBar_2 = view.findViewById(R.id.progressBar_2);       //12번 탄수화물
        progressBar_3 = view.findViewById(R.id.progressBar_3);       //13번 지방
        progressBar_4 = view.findViewById(R.id.progressBar_4);       //14번 나트륨

        ImageView iv_precautions1 = view.findViewById(R.id.iv_precautions1);
        ImageView iv_precautions2 = view.findViewById(R.id.iv_precautions2);
        ImageView iv_precautions3 = view.findViewById(R.id.iv_precautions3);
        ImageView iv_precautions4 = view.findViewById(R.id.iv_precautions4);
        ImageView iv_precautions5 = view.findViewById(R.id.iv_precautions5);

        ImageView[] iv_array = {iv_precautions1, iv_precautions2, iv_precautions3, iv_precautions4, iv_precautions5};

        AlertDialog.Builder builder = new AlertDialog.Builder(container.getContext());
        LinearLayout layout01 = (LinearLayout) view.findViewById(R.id.layout01);
        LinearLayout layout02 = (LinearLayout) view.findViewById(R.id.layout02);
        LinearLayout layout03 = (LinearLayout) view.findViewById(R.id.layout03);
        LinearLayout layout04 = (LinearLayout) view.findViewById(R.id.layout04);


        productinfo = new ArrayList<>();
        if (getArguments() != null) {
            productinfo = getArguments().getStringArrayList("productinfo");
            setinfo(tv_primary_fnclty, productinfo, 2); // 3번 주된기능성
            setinfo(tv_ntk_mthd, productinfo, 3);            // 4번 섭취방법
            setinfoamonut(tv_indiv_rawmtrl_nm, productinfo, 4);    // 5번 기능성원료
            setinfoamonut(tv_etc_rawmtrl_nm, productinfo, 5);      // 6번 기타원료
            setinfoamonut(tv_cap_rawmtrl_nm, productinfo, 6);      // 7번 캡슐 원료
            setPrecautions(iv_array, productinfo, 7);  // 8번 주의사항
            setinfo(tv_prdt_shap_cd_nm, productinfo, 8);      // 9번 제품형태
            double Protein = 0;
            double Carbohydrate = 0;
            double Fat = 0;
            double Salt = 0;
            if (productinfo.get(11)!=null) {
                Protein = UnitConversion(productinfo.get(11));
                tv_protein.setText(productinfo.get(11));
                tv_RIProtein.setText(Double.toString(Math.round(Protein / RIProtein * 100)) + '%');
                progressBar_1.setProgress((int) Math.round(Protein / RIProtein * 100));
            }
            if (productinfo.get(12)!=null) {
                Carbohydrate = UnitConversion(productinfo.get(12));
                tv_carbohydrate.setText(productinfo.get(12));
                tv_RICarbohydrate.setText(Double.toString(Math.round(Carbohydrate / RICarbohydrate * 100)) + '%');
                progressBar_2.setProgress((int) Math.round(Carbohydrate / RICarbohydrate * 100));

            }
            if (productinfo.get(13)!=null) {
                Fat = UnitConversion(productinfo.get(13));
                tv_fat.setText(productinfo.get(13));
                tv_RIFat.setText(Double.toString(Math.round(Fat / RIFat * 100)) + '%');
                progressBar_3.setProgress((int) Math.round(Fat / RIFat * 100));
            }
            if (productinfo.get(14)!=null) {
                Salt = UnitConversion(productinfo.get(14));
                tv_salt.setText(productinfo.get(14));
                tv_RISalt.setText(Double.toString(Math.round(Salt / RISalt * 100)) + '%');
                progressBar_4.setProgress((int) Math.round(Salt / RISalt * 100));
            }


            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.layout01:
                            builder.setTitle("기능성 원료");
                            builder.setMessage(productinfo.get(5));
                            builder.setPositiveButton("확인", null);
                            builder.create().show();
                            break;
                        case R.id.layout02:
                            builder.setTitle("기타 원료");
                            builder.setMessage(productinfo.get(6));
                            builder.setPositiveButton("확인", null);
                            builder.create().show();
                            break;
                        case R.id.layout03:
                            builder.setTitle("캡슐 원료");
                            builder.setMessage(productinfo.get(7));
                            builder.setPositiveButton("확인", null);
                            builder.create().show();
                            break;
                        case R.id.layout04:
                            builder.setTitle("주의사항");
                            builder.setMessage(productinfo.get(8));
                            builder.setPositiveButton("확인", null);
                            builder.create().show();
                            break;
                    }
                }
            };
            layout01.setOnClickListener(clickListener);
            layout02.setOnClickListener(clickListener);
            layout03.setOnClickListener(clickListener);
            layout04.setOnClickListener(clickListener);
        } else {
            Log.e("번들 이동 실패", "오류");
        }

        return view;
    }

    private double UnitConversion(String str) {
        double result = 0;
        if (str.contains("m")) {
            str = str.replace("mg", "");
            result = Double.parseDouble(str) * 0.001;
        }
        if (str.contains("g")) {
            str = str.replace("g", "");
         //   Log.e(str, str);
            result = Double.parseDouble(str);
        }
        return result;
    }

    public void setinfo(TextView view, ArrayList<String> arrayList, int a) {
        if (arrayList.size() > a) {
            if (arrayList.get(a + 1) != null) {
                view.setText(arrayList.get(a + 1));
            } else {
                view.setText("정보를 추가해주세요");
            }
        }
    }

    public void setPrecautions(ImageView[] views, ArrayList<String> arrayList, int a) {

        String precautions = arrayList.get(a + 1);
        if (arrayList.size() > a) {
            if (precautions != null) {
                if (precautions.contains("질환")) {
                    views[0].setColorFilter(Color.parseColor("#FFC81E"));
                }
                if (precautions.contains("알레르기")) {
                    views[1].setColorFilter(Color.parseColor("#FFC81E"));
                }
                if (precautions.contains("어린이")) {
                    views[2].setColorFilter(Color.parseColor("#FFC81E"));
                }
                if (precautions.contains("임신")) {
                    views[3].setColorFilter(Color.parseColor("#FFC81E"));
                }
                if (precautions.contains("섭취")) {
                    views[4].setColorFilter(Color.parseColor("#FFC81E"));
                }

            }
        }
    }

    public void setinfoamonut(TextView view, ArrayList<String> arrayList, int a) {
        if (arrayList.size() > a) {
            if (arrayList.get(a + 1) != null) {
                int amonut = 1;
                amonut = amonut + countChar(arrayList.get(a + 1), ',');
             //   Log.e("amonut", String.valueOf(amonut));
                view.setText(String.valueOf(amonut));
            } else {
                view.setText("0");
            }
        }
    }

    public int countChar(String str, char ch) {
        return str.length() - str.replace(String.valueOf(ch), "").length();
    }


}