package com.example.graduation;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragmentsurvey2 extends Fragment {
    public Fragmentsurvey2(){ }

    private String result;
    private TextView tv_category;
    private ListView listview ;
    private ListViewAdapter adapter;
    ArrayList<String> list_select = new ArrayList<String>();

    public static Fragmentsurvey2 newInstance() {
        return new Fragmentsurvey2();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentsurvey2, container, false);

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter 달기
        listview = (ListView) view.findViewById(R.id.sv_listview);
        listview.setAdapter(adapter);


        if (getArguments() != null)
        {
            result = getArguments().getString("category");

            switch( result ){
                case"다이어트":
                    tv_category = view.findViewById(R.id.sv_tv_category);
                    tv_category.setText("다이어트");

                    adapter.addItem("1", "중성 지방 감소");  //(제목 부분, 내용)
                    adapter.addItem("2", "콜레스테롤 조절");
                    adapter.addItem("3", "체중 감량");
                    adapter.addItem("4", "체지방 감소");
                    adapter.addItem("5", "근육량 증가");
                    adapter.addItem("6", "활력 증진");
                    adapter.addItem("7", "지방산 에너지 전환");
                    adapter.addItem("8", "운동 능력 향상");
                    adapter.addItem("9", "혈액 순환");
                    adapter.addItem("10", "혈압 조절");
                    adapter.addItem("11", "혈당 조절");
                    adapter.addItem("12", "내장 지방 감소");
                    adapter.addItem("13", "기초대사량 증가");
                    adapter.addItem("14", "에너지 생성");
                    adapter.addItem("15", "에너지 부스팅");

                    adapter.notifyDataSetChanged(); //어댑터의 변경을 알림


                    break;
                case"운동":
                    tv_category = view.findViewById(R.id.sv_tv_category);
                    tv_category.setText("운동");

                    adapter.addItem("1", "근육량 증가");  //(제목 부분, 이미지, 내용)
                    adapter.addItem("2", "운동 능력 향상");
                    adapter.addItem("3", "단백질 합성 자극");
                    adapter.addItem("4", "운동 피로 감소");
                    adapter.addItem("5", "에너지 부스팅");
                    adapter.addItem("6", "근력 강화");
                    adapter.addItem("7", "혈당 조절");
                    adapter.addItem("8", "근육 부피 증가");
                    adapter.addItem("9", "근육통 감소");
                    adapter.addItem("10", "근육 손실 방지");
                    adapter.addItem("11", "근육 활동 증가");
                    adapter.addItem("12", "근육 회복 촉진");

                    adapter.notifyDataSetChanged(); //어댑터의 변경을 알림

                    break;
                case"건강":
                    tv_category = view.findViewById(R.id.sv_tv_category);
                    tv_category.setText("건강");

                    adapter.addItem("1", "빈혈");  //(제목 부분, 이미지, 내용)
                    adapter.addItem("2", "건조한 머리카락");
                    adapter.addItem("3", "잇몸 출혈");
                    adapter.addItem("4", "무기력증");
                    adapter.addItem("5", "피부나 눈의 건조");
                    adapter.addItem("6", "불임, 근이영양증");
                    adapter.addItem("7", "혈액 응고 장애");
                    adapter.addItem("8", "각기병");
                    adapter.addItem("9", "설염 및 구내염");
                    adapter.addItem("10", "변비");
                    adapter.addItem("11", "약한 면연력 ");
                    adapter.addItem("12", "기억력 상실");
                    adapter.addItem("13", "당뇨");
                    adapter.addItem("14", "시력 감소");

                    adapter.notifyDataSetChanged(); //어댑터의 변경을 알림

                    break;
            }
        }

        FrameLayout frame =(FrameLayout)view.findViewById(R.id.sv_frame);
        //체크완료 추천화면으로 이동
        Button sv_btn_complete = (Button)view.findViewById(R.id.sv_btn_complete);
        sv_btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                //체크박스로 체크한 셀의 정보를 담고 있는 희소 논리 배열 얻어오기
                SparseBooleanArray checkedItems = listview.getCheckedItemPositions();

                Log.e("체크드아이템",checkedItems.toString());
                int count = adapter.getCount(); //전체 몇개인지 세기

                if(checkedItems.size()!=0){
                    list_select.add(checkedItems.toString());
                    bundle.putString("checked",list_select.toString());

                    Log.e("체크드",list_select.toArray().toString());
                    Fragmentsurvey3 fragmentsurvey3 = new Fragmentsurvey3();
                    fragmentsurvey3.setArguments(bundle);
                    replaceFragment(fragmentsurvey3);

                } else {
                    Toast.makeText(container.getContext(), "다시 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                listview.clearChoices() ;
                adapter.notifyDataSetChanged();

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
