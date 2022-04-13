package com.example.graduation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragmentreview extends Fragment {
    public static Fragmentreview newInstance() {
        return new Fragmentreview();
    }
    public Fragmentreview(){ }
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    public String pd_code;
    public RatingBar ratingbar;
    double avg = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        String rvinfo = getArguments().getString("pd_code");
        pd_code = rvinfo; //pd_code 받기.
        Log.e("pd_code",pd_code);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Review");
        List pdscore = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // 클래스 모델이 필요?
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    //MyFiles filename = (MyFiles) fileSnapshot.getValue(MyFiles.class);
                    //하위키들의 value를 어떻게 가져오느냐???
                    if (fileSnapshot.child(pd_code).getValue(Double.class) != null){
                        String aaa = fileSnapshot.child(pd_code).getValue(Double.class).toString();
                        Log.e("value is ", aaa);
                        pdscore.add(aaa);
                    }
                }
                Log.e("pdscore리스트",pdscore.toString());
                Log.e("pdscore리스트",pdscore.get(0).toString());

                String qwe = null;
                double sum = 0;

                for(int i = 0; i < pdscore.size(); i++){
                    qwe = pdscore.get(i).toString();
                    sum = sum + Double.parseDouble(qwe);
                    avg = sum / pdscore.size();
                }
                System.out.println("평균은 : "+avg);

                ratingbar = view.findViewById(R.id.rv_ratingBar);
                ratingbar.setRating((float) avg);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG: ", "Failed to read value", databaseError.toException());
            }
        });

        return view;
    }
}
