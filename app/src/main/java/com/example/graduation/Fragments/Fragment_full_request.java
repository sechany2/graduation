package com.example.graduation.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Adapter.MyReviewAdapter;
import com.example.graduation.Adapter.RequestAdapter;
import com.example.graduation.Object.Request;
import com.example.graduation.Object.UserAccount;
import com.example.graduation.R;
import com.google.firebase.auth.FirebaseAuth;
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
    private RequestAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String name;
    private FirebaseAuth mAuth;
    private ArrayList<String> arrayList3;
    private int flag;
    private boolean clickFlag = true;
    private String answer ;
    private Context ct;



    private AlertDialog.Builder dlg ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_request, container, false);
        ct = container.getContext();

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.rv_request);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        arrayList.clear();
        databaseReference = database.getReference();
        arrayList3 = new ArrayList<>();
        ValueEventListener uavalueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스에서 본인 정보 저장
                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                    UserAccount userAccount = snapshot2.getValue(UserAccount.class);
                    if (snapshot2.getKey().equals(mAuth.getUid())) {
                        name = userAccount.getName();       //이름 저장
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("error", error.toString());
            }
        };
        databaseReference.child("graduation").child("UserAccount").addValueEventListener(uavalueEventListener);

        databaseReference = database.getReference("Request");
        ValueEventListener RequestEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                //pdarrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    arrayList3.add(snapshot.getKey());
                    Request request = snapshot.getValue(Request.class);
                    request.setCategory("분석 요청");
                    arrayList.add(request);
                }
                databaseReference.removeEventListener(this);
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
                    flag = arrayList3.size();
                    arrayList3.add(snapshot.getKey());
                    Request request = snapshot.getValue(Request.class);
                    request.setCategory("질의 문의");
                    arrayList.add(request);
                }

                ArrayList<Request> arrayList2 = new ArrayList<>();

                if (name.equals("admin")) {
                    adapter = new RequestAdapter(arrayList, getContext());
                    adapter.setOnItemClickListener(new RequestAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int pos) {
                                LinearLayout rqlayout = v.findViewById(R.id.answer);
                                rqlayout.setVisibility((View.VISIBLE));
                            if (clickFlag) {
                                Button a_btn = v.findViewById(R.id.answer_btn);
                                EditText tv_rq_answer = v.findViewById(R.id.tv_rq_answer);
                                tv_rq_answer.setEnabled(true);

                                databaseReference = database.getReference();
                                a_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (pos < flag) {

                                            dlg = new AlertDialog.Builder(ct);
                                            dlg.setTitle("답변 완료");
                                            dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    answer = new String();
                                                    answer = tv_rq_answer.getText().toString();
                                                    databaseReference.child("Request").child(arrayList3.get(pos)).child("answer").setValue(answer);
                                                    tv_rq_answer.setEnabled(false);
                                                    a_btn.setVisibility(View.GONE);
                                                }
                                            });
                                            dlg.show();
                                        } else {
                                            dlg = new AlertDialog.Builder(ct);
                                            dlg.setTitle("답변 완료");
                                            dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    answer = new String();
                                                    answer = tv_rq_answer.getText().toString();
                                                    databaseReference.child("Inquiry").child(arrayList3.get(pos)).child("answer").setValue(answer);
                                                    tv_rq_answer.setEnabled(false);
                                                    a_btn.setVisibility(View.GONE);
                                                }
                                            });
                                            dlg.show();

                                        }
                                    }
                                });
                                if(tv_rq_answer.getText().toString().equals("")) {
                                    clickFlag = false;
                                }
                            }
                            else {
                                rqlayout.setVisibility(View.GONE);
                                clickFlag =true;
                            }
                        }
                    });
                } else {
                    for (Request snapshot : arrayList) {
                        if (snapshot.getName().equals(name)) {
                            arrayList2.add(snapshot);
                        }
                    }
                    adapter = new RequestAdapter(arrayList2, getContext());
                }

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침

                databaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB를 가져오던중 에러 발생 시
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        };
        databaseReference = database.getReference("Inquiry");
        databaseReference.addValueEventListener(InquiryEventListener);


        return view;
    }


}