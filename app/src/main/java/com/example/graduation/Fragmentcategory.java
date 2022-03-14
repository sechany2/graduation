package com.example.graduation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragmentcategory extends Fragment {
    public Fragmentcategory(){ }

    private RecyclerView recyclerView;
    private DietAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String result, category;
    private TextView tv_category, tv_semicategory;
    private Context context;



    public static Fragmentcategory newInstance() {
        return new Fragmentcategory();
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentcategory, container, false);

        if (getArguments() != null)
        {
            result = getArguments().getString("category");

            switch( result ){
                case"다이어트":
                    tv_category = view.findViewById(R.id.tv_category);
                    tv_category.setText("다이어트");
                    tv_semicategory = view.findViewById(R.id.tv_semicategory);
                    tv_semicategory.setText("다이어트 세부 메뉴");
                    break;
                case"벌크업":
                    tv_category = view.findViewById(R.id.tv_category);
                    tv_category.setText("벌크업");
                    tv_semicategory = view.findViewById(R.id.tv_semicategory);
                    tv_semicategory.setText("벌크업 세부 메뉴");
                    break;
                case"건강":
                    tv_category = view.findViewById(R.id.tv_category);
                    tv_category.setText("건강");
                    tv_semicategory = view.findViewById(R.id.tv_semicategory);
                    tv_semicategory.setText("건강 세부 메뉴");
                    break;
        }
            recyclerView = view.findViewById(R.id.rv_category);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            arrayList = new ArrayList<>();

            database = FirebaseDatabase.getInstance();

            databaseReference = database.getReference("Product");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                    arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                    for (DataSnapshot snapshot : datasnapshot.getChildren()){
                        Product product = snapshot.getValue(Product.class);
                        category = product.getPd_classification();
                        if(category.equals(result)){
                            arrayList.add(product);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            adapter = new DietAdapter(arrayList, getContext());
            recyclerView.setAdapter(adapter);
        }



        adapter.setOnItemClickListener(
                new DietAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {


                        //제품정보 저장
                        ArrayList<String> pdinfo= new ArrayList<>();
                        Bundle info = new Bundle();//제품정보 보낼 번들 info 생성
                        pdinfo.add(arrayList.get(pos).getPd_name());
                        pdinfo.add(arrayList.get(pos).getPd_brandname());
                        pdinfo.add(arrayList.get(pos).getPd_profile());
                        info.putStringArrayList("product",pdinfo);
                        FragmentProduct fragmentProduct = new FragmentProduct();
                        fragmentProduct.setArguments(info);
                        Log.e(info.getStringArrayList("product").get(0),info.getStringArrayList("product").get(1));
                        ((MainActivity)getActivity()).replaceFragment(fragmentProduct);//제품 페이지로 이동


                    }
                }
        );
        return view;
    }
}