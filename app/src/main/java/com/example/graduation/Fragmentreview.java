package com.example.graduation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

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
    public TextView ratingbar_tv, pb_tv5, pb_tv4, pb_tv3, pb_tv2, pb_tv1;
    public ProgressBar pb_5, pb_4, pb_3, pb_2, pb_1;
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
                ratingbar_tv = view.findViewById(R.id.ratingbar_tv);
                ratingbar_tv.setText(String.format("%.1f", avg) + "점");

                pb_5 = view.findViewById(R.id.pb_5);
                pb_4 = view.findViewById(R.id.pb_4);
                pb_3 = view.findViewById(R.id.pb_3);
                pb_2 = view.findViewById(R.id.pb_2);
                pb_1 = view.findViewById(R.id.pb_1);

                double progress5 = 0;
                double progress4 = 0;
                double progress3 = 0;
                double progress2 = 0;
                double progress1 = 0;

                double group5 = 0;
                double group4 = 0;
                double group3 = 0;
                double group2 = 0;
                double group1 = 0;

                String p5 = "5.0";
                String p4 = "4.0";
                String p3 = "3.0";
                String p2 = "2.0";
                String p1 = "1.0";

                for(int i = 0; i < pdscore.size(); i++){
                    if(pdscore.get(i).toString().equals(p5)){
                        group5 = group5 + 1;
                    }
                    else if(pdscore.get(i).toString().equals(p4)){
                        group4 = group4 + 1;
                    }
                    else if(pdscore.get(i).toString().equals(p3)){
                        group3 = group3 + 1;
                    }
                    else if(pdscore.get(i).toString().equals(p2)){
                        group2 = group2 + 1;
                    }
                    else if(pdscore.get(i).toString().equals(p1)){
                        group1 = group1 + 1;
                    }
                }
                Log.e("group5", String.valueOf(group5));
                Log.e("pdscore.size()", String.valueOf(pdscore.size()));

                progress5 = group5/Double.valueOf(pdscore.size());
                progress4 = group4/Double.valueOf(pdscore.size());
                progress3 = group3/Double.valueOf(pdscore.size());
                progress2 = group2/Double.valueOf(pdscore.size());
                progress1 = group1/Double.valueOf(pdscore.size());

                Log.e("progress5", String.valueOf(progress5));
                pb_5.setProgress((int) (progress5*100));
                pb_4.setProgress((int) (progress4*100));
                pb_3.setProgress((int) (progress3*100));
                pb_2.setProgress((int) (progress2*100));
                pb_1.setProgress((int) (progress1*100));

                pb_tv5 = view.findViewById(R.id.pb_tv5);
                pb_tv4 = view.findViewById(R.id.pb_tv4);
                pb_tv3 = view.findViewById(R.id.pb_tv3);
                pb_tv2 = view.findViewById(R.id.pb_tv2);
                pb_tv1 = view.findViewById(R.id.pb_tv1);

                pb_tv5.setText((int) (progress5*100) + "%");
                pb_tv4.setText((int) (progress4*100) + "%");
                pb_tv3.setText((int) (progress3*100) + "%");
                pb_tv2.setText((int) (progress2*100) + "%");
                pb_tv1.setText((int) (progress1*100) + "%");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG: ", "Failed to read value", databaseError.toException());
            }
        });

        return view;
    }
}
