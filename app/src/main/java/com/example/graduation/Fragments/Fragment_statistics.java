package com.example.graduation.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Adapter.CustomAdapter;
import com.example.graduation.Adapter.RankingAdapter;
import com.example.graduation.Logic.SortArraylist;
import com.example.graduation.Object.Product;
import com.example.graduation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Fragment_statistics extends Fragment {

    public static Fragment_statistics newInstance() {
        return new Fragment_statistics();
    }
    private RecyclerView recyclerView,recyclerView2;
    private RankingAdapter adapter,adapter2;
    private RecyclerView.LayoutManager layoutManager,layoutManager2;
    private ArrayList<Product> arrayList,arrayList2;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        Context ct = container.getContext();
        // 리사이클러뷰
        recyclerView = view.findViewById(R.id.rankview); //아디 연결
        recyclerView2 = view.findViewById(R.id.rankview2);
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존성능 강화
        recyclerView2.setHasFixedSize(true); //리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(ct, RecyclerView.HORIZONTAL, false);
        layoutManager2 = new LinearLayoutManager(ct, RecyclerView.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);
        arrayList = new ArrayList<>(); // 객체를 담을 어레이 리스트(어댑터쪽으로)
        arrayList2 = new ArrayList<>();

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동

        databaseReference = database.getReference("Product"); // DB테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                arrayList2.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    Product product = snapshot.getValue(Product.class);// 만들어뒀던 Product 객체에 데이터를 담는다.
                    if(arrayList.size()<10){
                        arrayList.add(product); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                        arrayList2.add(product);
                    }
                }
                Collections.sort(arrayList, new SortArraylist(0)); //추천 많이 받은 순서
                Collections.sort(arrayList2, new SortArraylist(1)); // 평점이 높은 순서
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB를 가져오던중 에러 발생 시
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
        adapter2 = new RankingAdapter(arrayList2, ct);
        adapter = new RankingAdapter(arrayList, ct);
        adapter.setOnItemClickListener(
                new RankingAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
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
                        replaceFragment(fragmentProduct);
                    }
                }
        );
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결
        adapter2.setOnItemClickListener(
                new RankingAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        ArrayList<String> pdinfo = new ArrayList<>();
                        Bundle info = new Bundle();//제품정보 보낼 번들 info 생성
                        pdinfo.add(arrayList2.get(pos).getPd_name());             // 0번 이름
                        pdinfo.add(arrayList2.get(pos).getPd_brandname());        // 1번 브랜드이름
                        pdinfo.add(arrayList2.get(pos).getPd_profile());          // 2번 이미지
                        pdinfo.add(arrayList2.get(pos).getPrimary_fnclty());      // 3번 주된기능성
                        pdinfo.add(arrayList2.get(pos).getNtk_mthd());            // 4번 섭취방법
                        pdinfo.add(arrayList2.get(pos).getIndiv_rawmtrl_nm());    // 5번 기능성원료
                        pdinfo.add(arrayList2.get(pos).getEtc_rawmtrl_nm());      // 6번 기타원료
                        pdinfo.add(arrayList2.get(pos).getCap_rawmtrl_nm());      // 7번 캡슐 원료
                        pdinfo.add(arrayList2.get(pos).getIftkn_atnt_matr_cn());  // 8번 주의사항
                        pdinfo.add(arrayList2.get(pos).getPrdt_shap_cd_nm());     // 9번 제품형태
                        pdinfo.add(arrayList2.get(pos).getPd_code());             // 10번 제품코드
                        pdinfo.add(arrayList2.get(pos).getPd_protein());          //11번 단백질
                        pdinfo.add(arrayList2.get(pos).getPd_carbohydrate());     //12번 탄수화물
                        pdinfo.add(arrayList2.get(pos).getPd_province());         //13번 지방
                        pdinfo.add(arrayList2.get(pos).getPd_salt());             //14번 나트륨


                        info.putStringArrayList("product", pdinfo);
                        FragmentProduct fragmentProduct = new FragmentProduct();
                        fragmentProduct.setArguments(info);
                        replaceFragment(fragmentProduct);
                    }
                }
        );
        recyclerView2.setAdapter(adapter2); //리사이클러뷰에 어댑터 연결



        return view;
    }
    //프래그먼트 이동 메소드
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).commit();
    }
}
