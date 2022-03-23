package com.example.graduation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragmentsurvey3 extends Fragment {
    public Fragmentsurvey3(){ }

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private String result;

    public static Fragmentsurvey3 newInstance() {
        return new Fragmentsurvey3();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentsurvey3, container, false);

        if (getArguments() != null) {
            result = getArguments().getString("category");
            recyclerView = view.findViewById(R.id.fg3_rv);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            arrayList = new ArrayList<>();
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference("Product");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);

                        category category = snapshot.child("category").getValue(category.class);
                        Log.e(getArguments().getString("checked"),"1");

                        if (category.getDiet() != null) {

                            if (category.getDiet().equals(result)) {


                                   arrayList.add(product);

                            }
                        }
                        if (category.getHealth() != null) {

                            if (category.getHealth().equals(result)) {

                                arrayList.add(product);
                            }
                        }
                        if (category.getBulkup() != null) {
                            if (category.getBulkup().equals(result)) {

                                arrayList.add(product);
                            }
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
        else {
            Log.e("s","s");
        }
        return view;
    }
}
