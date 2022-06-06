package com.example.graduation.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Activity.MainActivity;
import com.example.graduation.Adapter.DietAdapter;
import com.example.graduation.Adapter.MyloveAdapter;
import com.example.graduation.Object.Product;
import com.example.graduation.Object.UserAccount;
import com.example.graduation.Object.category;
import com.example.graduation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragmentmylove extends Fragment {
    public Fragmentmylove() {
    }

    public String name;
    private RecyclerView recyclerView;
    private MyloveAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> arrayList;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;     //변수 선언(파이어베이스 인증처리)
    private DatabaseReference databaseReference,dbrfrv,dbrfml;
    private String pdcode;

    private TextView tv_category, tv_semicategory, tv_cg_pd_rt;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentmylove, container, false);


            //좋아요 리사이클러뷰
            recyclerView = view.findViewById(R.id.rv_mylove);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            arrayList = new ArrayList<>();
            List pdscore = new ArrayList<>();

            database = FirebaseDatabase.getInstance();
            mAuth = FirebaseAuth.getInstance();
            databaseReference = database.getReference("Product");
            dbrfml = database.getReference("graduation").child("UserAccount").child(mAuth.getUid()).child("love");

            dbrfml.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                        pdcode = snapshot2.getKey();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                    arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                    for (DataSnapshot snapshot : datasnapshot.getChildren()){
                        Product product = snapshot.getValue(Product.class);
                        if(product.getPd_code()!=null){
                            if(product.getPd_code().equals(pdcode)){
                            arrayList.add(product);
                            }
                        }
                    }
                    dbrfrv = database.getReference("Review");
                    dbrfrv.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (int i = 0; i < arrayList.size(); i++) {
                                // 클래스 모델이 필요?
                                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                                    //MyFiles filename = (MyFiles) fileSnapshot.getValue(MyFiles.class);
                                    //하위키들의 value를 어떻게 가져오느냐???
                                    if (fileSnapshot.child(arrayList.get(i).getPd_code()).child("rate").getValue(Double.class) != null) {
                                        String aaa = fileSnapshot.child(arrayList.get(i).getPd_code()).child("rate").getValue(Double.class).toString();
                                        pdscore.add(aaa);
                                    }

                                }


                                String qwe = null;
                                double sum = 0;
                                double avg = 0;

                                for (int j = 0; j < pdscore.size(); j++) {
                                    qwe = pdscore.get(j).toString();
                                    sum = sum + Double.parseDouble(qwe);
                                    avg = sum / pdscore.size();
                                }

                                arrayList.get(i).setPd_avg((float) avg);

                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            adapter = new MyloveAdapter(arrayList, getContext());

            recyclerView.setAdapter(adapter);



            //아이템 클릭 이벤트
            adapter.setOnItemClickListener(
                    new MyloveAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int pos) {

                            //제품정보 저장
                            ArrayList<String> pdinfo = new ArrayList<>();
                            Bundle info = new Bundle();//제품정보 보낼 번들 info 생성
                            pdinfo.add(arrayList.get(pos).getPd_name());             // 0번 이름
                            pdinfo.add(arrayList.get(pos).getPd_brandname());        // 1번 브랜드이름
                            pdinfo.add(arrayList.get(pos).getPd_profile());          // 2번 이미지
                            pdinfo.add(arrayList.get(pos).getPrimary_fnclty());      // 3번 주된기능성
                            pdinfo.add(arrayList.get(pos).getNtk_mthd());            // 4번 섭취방법
                            pdinfo.add(arrayList.get(pos).getIndiv_rawmtrl_nm());    // 5번 기능성원료
                            pdinfo.add(arrayList.get(pos).getEtc_rawmtrl_nm());      // 6번 기타원료
                            pdinfo.add(arrayList.get(pos).getCap_rawmtrl_nm());      // 7번 캡슐 원료
                            pdinfo.add(arrayList.get(pos).getIftkn_atnt_matr_cn());  // 8번 주의사항
                            pdinfo.add(arrayList.get(pos).getPrdt_shap_cd_nm());     // 9번 제품형태
                            pdinfo.add(arrayList.get(pos).getPd_code());             // 10번 제품코드
                            pdinfo.add(arrayList.get(pos).getPd_protein());          //11번 단백질
                            pdinfo.add(arrayList.get(pos).getPd_carbohydrate());     //12번 탄수화물
                            pdinfo.add(arrayList.get(pos).getPd_province());         //13번 지방
                            pdinfo.add(arrayList.get(pos).getPd_salt());             //14번 나트륨

                            info.putStringArrayList("product", pdinfo);
                            FragmentProduct fragmentProduct = new FragmentProduct();
                            fragmentProduct.setArguments(info);
                            ((MainActivity) getActivity()).replaceFragment(fragmentProduct);//제품 페이지로 이동


                        }
                    }
            );

        return view;

    }
    }