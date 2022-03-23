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
import android.widget.LinearLayout;
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

public class Fragmentsurvey4 extends Fragment {
    public Fragmentsurvey4(){ }

    private String result;
    private TextView tv_category;
    private ListView listview ;
    private ListViewAdapter adapter;
    ArrayList<String> list_select = new ArrayList<String>();

    public static Fragmentsurvey4 newInstance() {
        return new Fragmentsurvey4();
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
                case"특정질환":
                    tv_category = view.findViewById(R.id.sv_tv_category);
                    tv_category.setText("특정질환");

                    adapter.addItem("1", "천식");  //(제목 부분, 내용)
                    adapter.addItem("2", "당뇨");
                    adapter.addItem("3", "심혈관질환");
                    adapter.addItem("4", "혈액응고장애");
                    adapter.addItem("5", "고칼슘혈증");
                    adapter.addItem("6", "기타");


                    adapter.notifyDataSetChanged(); //어댑터의 변경을 알림


                    break;
                case"알레르기":
                    tv_category = view.findViewById(R.id.sv_tv_category);
                    tv_category.setText("알레르기");

                    adapter.addItem("1", "대두");  //(제목 부분, 이미지, 내용)
                    adapter.addItem("2", "밀");
                    adapter.addItem("3", "게");
                    adapter.addItem("4", "새우");
                    adapter.addItem("5", "기타");

                    adapter.notifyDataSetChanged(); //어댑터의 변경을 알림

                    break;

            }
        }

        FrameLayout frame =(FrameLayout)view.findViewById(R.id.sv_frame);
        LinearLayout titlelayout = (LinearLayout)view.findViewById(R.id.titlelayout);
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


                    list_select.add(checkedItems.toString());
                    if(result.equals("알레르기")) {
                        bundle.putString("allergy", list_select.toString());
                    }
                    if(result.equals("특정질환")) {
                       bundle.putString("disease", list_select.toString());
                     }
                    bundle.putString("pregnant",getArguments().getString("pregnant"));
                    bundle.putString("baby",getArguments().getString("baby"));


                Fragmentsurvey0 fragmentsurvey0 = new Fragmentsurvey0();
                fragmentsurvey0.setArguments(bundle);
                replaceFragment(fragmentsurvey0);





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
