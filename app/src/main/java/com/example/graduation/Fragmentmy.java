package com.example.graduation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragmentmy extends Fragment {
    public TextView my_name, my_rvcount;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private ArrayList<Object> arrayList2;
    public Fragmentmy(){ }
    String name;
    String rvcount="0";

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
                //파이어베이스에서 본인 정보 저장
                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                    UserAccount userAccount = snapshot2.getValue(UserAccount.class);
                    if (snapshot2.getKey().equals(mAuth.getUid())) {
                        name = userAccount.getName();       //이름 저장
                    }
                }
                my_name.setText(name);
                arrayList2 = new ArrayList<>();
                ValueEventListener rvwtvalueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //파이어베이스에서 본인 정보 저장
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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("error", error.toString());
            }
        };



        databaseReference.child("graduation").child("UserAccount").addValueEventListener(uavalueEventListener);



        return view;
    }
}
