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
import com.example.graduation.Object.MyReview;
import com.example.graduation.Object.Product;
import com.example.graduation.Object.Review;
import com.example.graduation.Object.UserAccount;
import com.example.graduation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragmentmyreview extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference,abrfpd,dbrf,dbrreviewwrite;
    private FirebaseAuth mAuth;
    private ArrayList<Product> pdarrayList;
    private ArrayList<MyReview> arrayList;
    private ArrayList<Review> arrayList2;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public String pd_code, pd_name, name;
    public Fragmentmyreview(){ }

    public static Fragmentmyreview newInstance() {
        return new Fragmentmyreview();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentmyreview, container, false);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Review");
        dbrf = database.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.myreview_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        name = null;

        ValueEventListener uavalueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //???????????????????????? ?????? ?????? ??????
                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                    UserAccount userAccount = snapshot2.getValue(UserAccount.class);
                    if (snapshot2.getKey().equals(mAuth.getUid())) {
                        name = userAccount.getName();       //?????? ??????
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("error", error.toString());
            }
        };
        dbrf.child("graduation").child("UserAccount").addValueEventListener(uavalueEventListener);
        dbrf.removeEventListener(uavalueEventListener);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // ??????????????? ????????? List??? ????????????
                    if (name.equals("admin")){
                        if(getArguments()!=null) {
                            name = getArguments().getString("name");
                        }
                    }
                    if (snapshot.getKey().equals(name)){
                        for(DataSnapshot snapshot2 : snapshot.getChildren()){
                            pd_code = snapshot2.getKey();
                            String score = snapshot2.child("rate").getValue().toString();
                            MyReview review = new MyReview(); // ??????????????? Review ????????? ???????????? ?????????.
                            //MyReview myreview = snapshot.getValue(MyReview.class);
                            review.setUserid(name);
                            review.setPd_code(pd_code);
                            review.setScore(score);
                            arrayList.add(review);
                        }
                    }

                }
                //
                abrfpd = database.getReference("Product"); // DB????????? ??????
                abrfpd.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //?????????????????? ????????????????????? ???????????? ???????????? ???
                        //pdarrayList.clear(); // ?????? ?????????????????? ?????????????????? ?????????
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){ // ??????????????? ????????? List??? ????????????
                            pd_code = snapshot.child("pd_code").getValue().toString();
                            for(int i=0; i<arrayList.size(); i++){
                                if(pd_code.equals(arrayList.get(i).getPd_code())){
                                    pd_name = snapshot.child("pd_name").getValue().toString();
                                    arrayList.get(i).setPd_name(pd_name);
                                }
                            }
                        }
                        adapter.notifyDataSetChanged(); // ????????? ?????? ??? ????????????
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // DB??? ??????????????? ?????? ?????? ???
                        Log.e("MainActivity", String.valueOf(databaseError.toException())); // ????????? ??????
                    }
                });
                //?????? ?????? ????????????
                dbrreviewwrite = database.getReference("Reviewwrite");
                dbrreviewwrite.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //arrayList.clear();
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                                    for(DataSnapshot snapshot3 : snapshot2.getChildren()){
                                        pd_code = snapshot3.getKey();
                                        String userid = dataSnapshot.getKey();
                                        if(userid.equals(name)){
                                            if (dataSnapshot.child(pd_code).getValue(String.class) != null) {
                                                String rvwrite = dataSnapshot.child(pd_code).getValue(String.class);
                                                for (int i=0; i<arrayList.size();i++){
                                                    if(pd_code.equals(arrayList.get(i).getPd_code())){
                                                        arrayList.get(i).setReview(rvwrite);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
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

        adapter = new MyReviewAdapter(arrayList,getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
