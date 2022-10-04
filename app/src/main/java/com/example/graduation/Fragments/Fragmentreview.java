package com.example.graduation.Fragments;

import static com.example.graduation.R.id.reviewRate;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Activity.MainActivity;
import com.example.graduation.Adapter.ReviewAdapter;
import com.example.graduation.Object.Review;
import com.example.graduation.Object.Review_write;
import com.example.graduation.Object.UserAccount;
import com.example.graduation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fragmentreview extends Fragment {
    public static Fragmentreview newInstance() {
        return new Fragmentreview();
    }
    public Fragmentreview(){ }
    private FirebaseDatabase database;
    private DatabaseReference databaseReference,dbrreviewwrite;
    public String pd_code,name,review_write, userid;
    public RatingBar ratingbar;
    public TextView ratingbar_tv, pb_tv5, pb_tv4, pb_tv3, pb_tv2, pb_tv1, reviewReview, reviewUserid, reviewRate;
    public ProgressBar pb_5, pb_4, pb_3, pb_2, pb_1;
    double avg = 0;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Review> arrayList;
    private ArrayList<Review_write> arrayList2;
    private FirebaseAuth mAuth;
    private boolean sortflag = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        String rvinfo = getArguments().getString("pd_code");
        pd_code = rvinfo; //pd_code 받기.
        //Log.e("pd_code",pd_code);
        TextView tv_sort = view.findViewById(R.id.tv_sort);
        recyclerView = view.findViewById(R.id.review_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<Review>();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Review");
        List pdscore = new ArrayList<>();
        reviewRate = view.findViewById(R.id.reviewRate);
        reviewUserid = view.findViewById(R.id.reviewUserid);

        tv_sort.setOnClickListener(new View.OnClickListener() {   //정렬버튼
            @Override
            public void onClick(View view) {
                if (sortflag){
                    tv_sort.setText("⇅ 최고별점순");
                    Collections.sort(arrayList, Collections.reverseOrder());
                    adapter.notifyDataSetChanged();
                    sortflag = false;}
                else{
                    tv_sort.setText("⇅ 최신등록순");
                    Collections.sort(arrayList, new Review());
                    sortflag = true;
                    adapter.notifyDataSetChanged();
                }
            }
        });

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

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //리뷰 출력
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // 반복문으로 데이터 List를 추출해냄
                    //Review review = snapshot.child(pd_code).getValue(Review.class);
                    //Log.e(snapshot.child(pd_code).getValue(Review.class),"점수");
                    //Log.e("review",review.getUserID());
                    if (snapshot.child(pd_code).child("rate").getValue(Double.class) != null){
                        String score = snapshot.child(pd_code).child("rate").getValue(Double.class).toString();
                        score=score.replace(".0","");
                        String date = snapshot.child(pd_code).child("date").getValue(String.class);
                        date = date.substring(0,10);
                        String userid = snapshot.getKey();
                        Review review = new Review(); // 만들어뒀던 Review 객체에 데이터를 담는다.
                        review.setUserid(userid);
                        review.setScore(score);
                        review.setDate(date);

                        //review.getScore() = snapshot.child(pd_code).getValue(Double.class);
                        //Log.e("아이디",userid);
                        //reviewRate.setText(score);
                        //reviewUserid.setText(userid);
                        arrayList.add(review);
                    }
                    //Review review = snapshot.getValue(Review.class);
                    //arrayList.add(review);// 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비

                }
                Collections.sort(arrayList, new Review());
                adapter.notifyDataSetChanged();

                // 클래스 모델이 필요?
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    //MyFiles filename = (MyFiles) fileSnapshot.getValue(MyFiles.class);
                    //하위키들의 value를 어떻게 가져오느냐???
                    if (fileSnapshot.child(pd_code).child("rate").getValue(Double.class) != null){
                        String aaa = fileSnapshot.child(pd_code).child("rate").getValue(Double.class).toString();
                        //Log.e("value is ", aaa);
                        pdscore.add(aaa);
                    }
                }
                //Log.e("pdscore리스트",pdscore.toString());
                //Log.e("pdscore리스트",pdscore.get(0).toString());

                String qwe = null;
                double sum = 0;

                for(int i = 0; i < pdscore.size(); i++){
                    qwe = pdscore.get(i).toString();
                    sum = sum + Double.parseDouble(qwe);
                    avg = sum / pdscore.size();
                }
                //System.out.println("평균은 : "+avg);

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
                //Log.e("group5", String.valueOf(group5));
                //Log.e("pdscore.size()", String.valueOf(pdscore.size()));

                progress5 = group5/Double.valueOf(pdscore.size());
                progress4 = group4/Double.valueOf(pdscore.size());
                progress3 = group3/Double.valueOf(pdscore.size());
                progress2 = group2/Double.valueOf(pdscore.size());
                progress1 = group1/Double.valueOf(pdscore.size());

                //Log.e("progress5", String.valueOf(progress5));
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


        reviewReview = view.findViewById(R.id.reviewReview);
        //리뷰 쓴거 불러오기
        dbrreviewwrite = database.getReference("Reviewwrite");
        dbrreviewwrite.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //arrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String userid = dataSnapshot.getKey();
                    //Review_write review_write = new Review_write();
                    if (dataSnapshot.child(pd_code).getValue(String.class) != null) {
                        String rvwrite = dataSnapshot.child(pd_code).getValue(String.class);
                        //Log.e("리뷰쓴거", rvwrite);
                        //review.setReview(rvwrite);
                        String idx = dataSnapshot.getKey();
                        for (int i=0; i<arrayList.size();i++){
                            //Log.e("유저아이디",userid);
                            //Log.e("리스트아이디",arrayList.get(i).getUserid());
                            if(userid.equals(arrayList.get(i).getUserid())){
                                //  Log.e("가나다라마바사","123");
                                arrayList.get(i).setReview(rvwrite);
                            }
                        }
                    }
                    //Log.e("리뷰글",dataSnapshot.child(pd_code).getValue(String.class));
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new ReviewAdapter(arrayList, arrayList2,getContext());
        recyclerView.setAdapter(adapter);

        Button btn_rv_write = (Button)view.findViewById(R.id.rv_write);

        btn_rv_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle rvinfo = new Bundle();
                rvinfo.putString("pd_code",pd_code);
                Fragmentreview_write fragmentreview_write = new Fragmentreview_write();
                fragmentreview_write.setArguments(rvinfo);
                ((MainActivity)getActivity()).replaceframeProduct(fragmentreview_write); //메인 엑티비티에 프래그먼트 이동 메소드 호출
            }
        });
        return view;
    }
}
