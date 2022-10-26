package com.example.graduation.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.graduation.Activity.MainActivity;
import com.example.graduation.Object.Review_write;
import com.example.graduation.Object.UserAccount;
import com.example.graduation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class Fragmentreview_write extends Fragment {
    public static Fragmentreview_write newInstance() {
        return new Fragmentreview_write();
    }
    public Fragmentreview_write(){ }
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    double rate;

    public String pd_code,name,pd_name, getTime;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_write, container, false);

        String rvinfo = getArguments().getString("pd_code");
        pd_code = rvinfo; //pd_code 받기.
        pd_name = getArguments().getString("pd_name");
        EditText reviewEdit=(EditText)view.findViewById(R.id.reviewEdit);
        Button cancelButton=(Button)view.findViewById(R.id.cancelButton);
        Button okButton=(Button)view.findViewById(R.id.okButton);
        RatingBar reviewRating=(RatingBar)view.findViewById(R.id.reviewRating);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        TimeZone tz;                                        // 객체 생성
        tz = TimeZone.getTimeZone("Asia/Seoul");  // TimeZone에 표준시 설정
        Date date = new Date();
        SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd kk:mm", Locale.KOREAN);
        now.setTimeZone(tz);
        getTime = now.format(date);


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
        databaseReference.removeEventListener(uavalueEventListener);



        reviewRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate = rating;
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReview_write(reviewEdit.getText().toString());
                Bundle rvinfo = new Bundle();
                rvinfo.putString("pd_code",pd_code);
                rvinfo.putString("pd_name",pd_name);
                Fragmentreview  fragmentreview = new Fragmentreview();
                fragmentreview.setArguments(rvinfo);
                ((MainActivity)getActivity()).replaceframeProduct(fragmentreview); //메인 엑티비티에 프래그먼트 이동 메소드 호출
            }
        });

        return view;
    }

    public void addReview_write(String review){


        databaseReference.child("Review").child(name).child(pd_code).child("date").setValue(getTime);
        databaseReference.child("Reviewwrite").child(name).child(pd_code).setValue(review);
        databaseReference.child("Review").child(name).child(pd_code).child("rate").setValue(rate);

    }
}
