package com.example.graduation.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Adapter.MyReviewAdapter;
import com.example.graduation.Adapter.RequestAdapter;
import com.example.graduation.Object.Request;
import com.example.graduation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_full_request extends Fragment {
    public Fragment_full_request() {
    }

    public static Fragment_full_request newInstance() {
        Fragment_full_request fragment = new Fragment_full_request();
        return fragment;
    }

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<Request> arrayList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_request, container, false);

        database = FirebaseDatabase.getInstance();

        recyclerView = view.findViewById(R.id.rv_request);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        arrayList.clear();

        databaseReference = database.getReference("Request");
        ValueEventListener RequestEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                //pdarrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄

                        Request request = snapshot.getValue(Request.class);
                        request.setCategory("분석 요청");
                        arrayList.add(request);

                }

                // adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB를 가져오던중 에러 발생 시
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        };
        databaseReference.addValueEventListener(RequestEventListener);

        databaseReference = database.getReference("Inquiry");
        ValueEventListener InquiryEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                //pdarrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄


                        Request request = snapshot.getValue(Request.class);
                        request.setCategory("질의 문의");
                        arrayList.add(request);

                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB를 가져오던중 에러 발생 시
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        };
        databaseReference.addValueEventListener(InquiryEventListener);

        adapter = new RequestAdapter(arrayList, getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }


}