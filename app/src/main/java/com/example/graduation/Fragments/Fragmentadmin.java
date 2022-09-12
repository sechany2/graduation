package com.example.graduation.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Activity.MainActivity;
import com.example.graduation.Adapter.UserAdapter;
import com.example.graduation.Object.User;
import com.example.graduation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragmentadmin extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private ArrayList<User> arrayList;
    private UserAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public Fragmentadmin() {
    }


    public static Fragmentadmin newInstance() {
        return new Fragmentadmin();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentadmin, container, false);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getInstance().getReference();
        recyclerView = view.findViewById(R.id.rv_user);
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
        ValueEventListener userEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    User user = new User();
                    for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                        if (snapshot2.getKey().equals("email")) {
                            user.setUserID(snapshot2.getValue().toString());
                        }
                        if (snapshot2.getKey().equals("name")) {
                            user.setName(snapshot2.getValue().toString());
                        }
                        if (snapshot2.getKey().equals("password")) {
                            user.setPassword(snapshot2.getValue().toString());
                        }
                    }
                    arrayList.add(user);// 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        databaseReference.child("graduation").child("UserAccount").addValueEventListener(userEventListener);

        adapter = new UserAdapter(arrayList, getContext());
        adapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Bundle bundle = new Bundle();
                String name =arrayList.get(pos).getName();
                bundle.putString("name",name);

                Fragmentmyreview fragmentmyreview = new Fragmentmyreview();
                fragmentmyreview.setArguments(bundle);
                ((MainActivity)getActivity()).replaceFragment(fragmentmyreview);
            }
        });
        recyclerView.setAdapter(adapter);
        databaseReference.removeEventListener(userEventListener);


        return view;
    }
}
