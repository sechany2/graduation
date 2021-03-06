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

import com.example.graduation.Activity.MainActivity;
import com.example.graduation.Object.Mylove;
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

public class Fragmentmy extends Fragment {
    public TextView my_name, my_rvcount;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private ArrayList<Object> arrayList2;
    private ArrayList<Object> arrayList3;
    public Fragmentmy(){ }
    public String name;
    public String rvcount="0";
    public String rqcount="0";
    public String mlcount="0";
    public int count  = 0;
    public static Fragmentmy newInstance() {
        return new Fragmentmy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentmy, container, false);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        my_name = view.findViewById(R.id.my_name);
        my_rvcount = view.findViewById(R.id.my_rvcount);

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
                my_name.setText(name);
                arrayList2 = new ArrayList<>();
                ValueEventListener rvwtvalueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //???????????????????????? ?????? ?????? ??????
                        for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                            if (snapshot2.getValue() != null) {
                                arrayList2.add(snapshot2.getValue());
                                rvcount = String.valueOf(arrayList2.size());
                            }
                        }
                        my_rvcount.setText(rvcount);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                databaseReference.child("Review").child(name).addValueEventListener(rvwtvalueEventListener);
                ValueEventListener rqEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //???????????????????????? ?????? ?????? ??????

                        for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                            if (snapshot2.getValue() != null) {
                                Request request = new Request();
                                request =  snapshot2.getValue(Request.class);

                                if(request.getName().equals(name)){
                                    count++;
                                }
                                rqcount = String.valueOf(count);
                            }
                        }
                        TextView tv_rq_count = view.findViewById(R.id.tv_rq_count);
                        tv_rq_count.setText(rqcount);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };

                count =0;
                databaseReference.child("Request").addValueEventListener(rqEventListener);
                databaseReference.removeEventListener(rqEventListener);
                databaseReference.child("Inquiry").addValueEventListener(rqEventListener);

                arrayList3 = new ArrayList<>();
                ValueEventListener mlEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                            if (snapshot2.getValue() != null) {
                                arrayList3.add(snapshot2.getValue());
                                mlcount = String.valueOf(arrayList3.size());
                            }
                        }
                        TextView tv_love_count = view.findViewById(R.id.tv_love_count);
                        tv_love_count.setText(mlcount);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };

                databaseReference.child("graduation").child("UserAccount").child(mAuth.getUid()).child("love").addValueEventListener(mlEventListener);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("error", error.toString());
            }
        };

        TextView my_write_review = view.findViewById(R.id.my_write_review);
        my_write_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragmentmyreview fragmentmyreview = new Fragmentmyreview();
                ((MainActivity)getActivity()).replaceFragment(fragmentmyreview); //?????? ??????????????? ??????????????? ?????? ????????? ??????
            }
        });

        TextView my_rq_btn = view.findViewById(R.id.tv_rq_btn);
        my_rq_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_full_request fragmentfr = new Fragment_full_request();
                ((MainActivity)getActivity()).replaceFragment(fragmentfr); //?????? ??????????????? ??????????????? ?????? ????????? ??????
            }
        });

        TextView my_love_btn = view.findViewById(R.id.tv_love_btn);
        my_love_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragmentmylove fragmentmylove = new Fragmentmylove();
                ((MainActivity)getActivity()).replaceFragment(fragmentmylove); //?????? ??????????????? ??????????????? ?????? ????????? ??????
            }
        });



        databaseReference.child("graduation").child("UserAccount").addValueEventListener(uavalueEventListener);



        return view;
    }
}
