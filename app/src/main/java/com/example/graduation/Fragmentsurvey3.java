package com.example.graduation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Fragmentsurvey3 extends Fragment {


    public Fragmentsurvey3() {
    }

    private String flagBundle = "0";
    private RecyclerView recyclerView;
    private ArrayList<String> productList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> arrayList, arrayListSort;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String result, name;
    public RatingBar ratingbar;
    public TextView fg3_pdrb_tv, fg3_usrb_tv;
    private FirebaseAuth mAuth;
    private HashMap<String, HashMap> user;
    private HashMap<String, Double> userReview, resultknn;
    private int omega3 = 0, probiotics = 0, roughage = 0, calcium = 0, protein = 0, vitaminb = 0, coq10 = 0, l_carnitine = 0, arginine = 0, l_glutamine = 0, creatine = 0, bcaa = 0, beta_alanine = 0, hmb = 0, vitamina = 0, vitaminc = 0, vitamind = 0, vitamine = 0, vitamink = 0, mvitamin = 0,
            propolis = 0, red_ginseng = 0, lutein = 0;

    public static Fragmentsurvey3 newInstance() {
        return new Fragmentsurvey3();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentsurvey3, container, false);

        if (getArguments() != null) {
            arrayListSort = new ArrayList<>();
            result = getArguments().getString("category");
            recyclerView = view.findViewById(R.id.fg3_rv);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            productList = new ArrayList<>();
            arrayList = new ArrayList<>();
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();
            name = null;
            List pdscore = new ArrayList<>();

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

            ValueEventListener rvvalueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dsnapshot) { //파이어베이스에서 리뷰정보 저장
                    user = new HashMap<String, HashMap>();
                    for (DataSnapshot snapshot3 : dsnapshot.getChildren()) {
                        userReview = null;
                        userReview = paramMap(snapshot3.getValue());
                        if (userReview != null) {
                            user.put(snapshot3.getKey(), userReview);
                        }
                    }
                    // databaseReference.removeEventListener(this);
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("error", error.toString());
                }
            };

            ValueEventListener pdvalueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                    productList.clear();
                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {

                        productList.add(snapshot.getKey());
                    }

                    resultknn = new HashMap<String, Double>();
                    double[] avg = new double[user.size()];




                    resultknn = new PearsonCorrelation().knn(name, user, productList, 5);  //상관계수 구하기





                    arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화


                    for (DataSnapshot snapshot4 : datasnapshot.getChildren()) {
                        Product product = snapshot4.getValue(Product.class);

                        category category = snapshot4.child("category").getValue(category.class);
                        pd_classification pd_classification = snapshot4.child("pd_classification").getValue(pd_classification.class);

                        categoryClassification(category);//카테고리 별 분류

                        arraylistAdd(product, snapshot4, pd_classification);//배열에 추가

                        //adapter.notifyDataSetChanged();//리사이클러뷰 업데이트
                        // databaseReference.removeEventListener(this);

                    }
                    ValueEventListener rvvalueEventListener2 = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            for (int i = 0; i < arrayListSort.size(); i++) {
                                // 클래스 모델이 필요?
                                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                                    //MyFiles filename = (MyFiles) fileSnapshot.getValue(MyFiles.class);
                                    //하위키들의 value를 어떻게 가져오느냐???
                                    if (fileSnapshot.child(arrayListSort.get(i).getPd_code()).getValue(Double.class) != null) {
                                        String aaa = fileSnapshot.child(arrayListSort.get(i).getPd_code()).getValue(Double.class).toString();
                                        pdscore.add(aaa);
                                    }

                                }


                                String qwe = null;
                                double sum = 0;
                                double avg = 0;

                                for (int j = 0; j < pdscore.size(); j++) {
                                    qwe = pdscore.get(j).toString();
                                    sum = sum + Double.parseDouble(qwe);
                                    avg = sum / pdscore.size();
                                }

                                arrayList.get(i).setPd_avg((float) avg);
                            }
                            adapter.notifyDataSetChanged();//리사이클러뷰 업데이트
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("TAG: ", "Failed to read value", databaseError.toException());
                        }
                    };
                    databaseReference.child("Review").addValueEventListener(rvvalueEventListener2);
                    databaseReference.removeEventListener(rvvalueEventListener2);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("error", error.toString());
                }
            };


            databaseReference.child("Review").addValueEventListener(rvvalueEventListener);
            databaseReference.child("graduation").child("UserAccount").addValueEventListener(uavalueEventListener);
            databaseReference.child("Product").addValueEventListener(pdvalueEventListener);


            databaseReference.removeEventListener(rvvalueEventListener);
            databaseReference.removeEventListener(uavalueEventListener);


            databaseReference.removeEventListener(pdvalueEventListener);
            adapter = new Fg3Adapter(arrayListSort, getContext());

            recyclerView.setAdapter(adapter);  //리사이클러뷰 출력
        } else {
            Log.e("error", "error");
        }
        return view;
    }


    public HashMap<String, Double> paramMap(Object object) {

        HashMap<String, Double> hashmap = new HashMap<String, Double>();

        try {

            JSONObject json = new JSONObject(object.toString());
            Iterator i = json.keys(); // json key 요소읽어옴

            while (i.hasNext()) {

                String k = i.next().toString(); // key 순차적으로 추출

                hashmap.put(k, Double.valueOf(json.getString(k))); // key, value를 map에 삽입
            }
        } catch (JSONException err) {
            Log.e("Exception : ", err.toString());
        }// 받아온 string을 json 으로로 변환


        return hashmap;
    }

    public void productIf(int classifcation, Product product, pd_classification pd_classification, DataSnapshot snapshot) {
        boolean flagboolean = true;

        if (!(getArguments().getString("pregnant").equals(flagBundle))) {
            if (product.getIftkn_atnt_matr_cn() != null) {
                if (product.getIftkn_atnt_matr_cn().contains("임산부")) {

                    flagboolean = false;
                }
            }
        }
        if (!(getArguments().getString("baby").equals(flagBundle))) {
            if (product.getIftkn_atnt_matr_cn() != null) {
                if (product.getIftkn_atnt_matr_cn().contains("유아")) {

                    flagboolean = false;
                }
            }
        }
        if (!(getArguments().getString("allergy").equals(flagBundle))) {
            if (product.getIftkn_atnt_matr_cn() != null) {
                if (getArguments().getString("allergy").equals("0")) {
                    if (product.getIftkn_atnt_matr_cn().contains("대두")) {

                        flagboolean = false;
                    }
                }
                if (getArguments().getString("allergy").equals("1")) {
                    if (product.getIftkn_atnt_matr_cn().contains("밀")) {

                        flagboolean = false;
                    }
                }
                if (getArguments().getString("allergy").equals("2")) {
                    if (product.getIftkn_atnt_matr_cn().contains("게")) {

                        flagboolean = false;
                    }
                }
                if (getArguments().getString("allergy").equals("3")) {
                    if (product.getIftkn_atnt_matr_cn().contains("새우")) {

                        flagboolean = false;
                    }
                }
                if (getArguments().getString("allergy").equals("4")) {
                    if (product.getIftkn_atnt_matr_cn().contains("알레르기")) {

                        flagboolean = false;
                    }
                }

            }
        }
        if (!(getArguments().getString("disease").equals(flagBundle))) {
            if (product.getIftkn_atnt_matr_cn() != null) {
                Log.e(getArguments().getString("disease"), "");
                if (getArguments().getString("disease").equals("0")) {
                    if (product.getIftkn_atnt_matr_cn().contains("천식")) {
                        flagboolean = false;
                    }
                }
                if (getArguments().getString("disease").equals("1")) {
                    if (product.getIftkn_atnt_matr_cn().contains("당뇨")) {
                        flagboolean = false;
                    }
                }
                if (getArguments().getString("disease").equals("2")) {
                    if (product.getIftkn_atnt_matr_cn().contains("심혈관질환")) {
                        flagboolean = false;
                    }
                }
                if (getArguments().getString("disease").equals("3")) {
                    if (product.getIftkn_atnt_matr_cn().contains("혈액응고장애")) {
                        flagboolean = false;
                    }
                }
                if (getArguments().getString("disease").equals("4")) {
                    if (product.getIftkn_atnt_matr_cn().contains("고칼슘혈증")) {
                        flagboolean = false;
                    }
                }
                if (getArguments().getString("disease").equals("5")) {
                    if (product.getIftkn_atnt_matr_cn().contains("질환")) {
                        flagboolean = false;
                    }
                }
            }
        }
        if (flagboolean) {
            if (ifknn(snapshot.getKey())) {
                if (!arrayList.contains(product)) {
                    arrayList.add(product);
                }
            }
        }
    }


    public boolean ifknn(String product) {
        boolean result = false;
        for (Map.Entry<String, Double> entry : resultknn.entrySet()) {

            if (product.equals(entry.getKey())) {
                result = true;

            }
        }

        return result;
    }

    public void categoryClassification(category category) {
        if (category.getDiet() != null) {

            if (category.getDiet().equals(result)) {
                if (getArguments().getString("checked").contains("0")) {
                    omega3++;
                }
                if (getArguments().getString("checked").contains("1")) {
                    probiotics++;
                    calcium++;
                }
                if (getArguments().getString("checked").contains("2")) {
                    roughage++;
                    coq10++;
                    l_glutamine++;
                }
                if (getArguments().getString("checked").contains("3")) {
                    calcium++;
                    arginine++;
                    l_glutamine++;
                }
                if (getArguments().getString("checked").contains("4")) {
                    protein++;
                    arginine++;
                }
                if (getArguments().getString("checked").contains("5")) {
                    vitaminb++;
                    coq10++;
                }
                if (getArguments().getString("checked").contains("6")) {
                    l_carnitine++;
                }
                if (getArguments().getString("checked").contains("7")) {
                    l_glutamine++;
                }
                if (getArguments().getString("checked").contains("8")) {
                    omega3++;
                }
                if (getArguments().getString("checked").contains("9")) {
                    probiotics++;
                    calcium++;
                }
                if (getArguments().getString("checked").contains("10")) {
                    roughage++;
                    protein++;
                }
                if (getArguments().getString("checked").contains("11")) {
                    calcium++;
                }
                if (getArguments().getString("checked").contains("12")) {
                    vitaminb++;
                    coq10++;
                }
                if (getArguments().getString("checked").contains("13")) {
                    vitaminb++;
                    coq10++;
                }
                if (getArguments().getString("checked").contains("14")) {
                    arginine++;
                }


                if (category.getDiet().equals(result)) {
                    if (getArguments().getString("checked").contains("0")) {
                        omega3++;
                    }
                    if (getArguments().getString("checked").contains("1")) {
                        probiotics++;
                        calcium++;
                    }
                    if (getArguments().getString("checked").contains("2")) {
                        roughage++;
                        coq10++;
                        l_glutamine++;
                    }
                    if (getArguments().getString("checked").contains("3")) {
                        calcium++;
                        arginine++;
                        l_glutamine++;
                    }
                    if (getArguments().getString("checked").contains("4")) {
                        protein++;
                        arginine++;
                    }
                    if (getArguments().getString("checked").contains("5")) {
                        vitaminb++;
                        coq10++;
                    }
                    if (getArguments().getString("checked").contains("6")) {
                        l_carnitine++;
                    }
                    if (getArguments().getString("checked").contains("7")) {
                        l_glutamine++;
                    }
                    if (getArguments().getString("checked").contains("8")) {
                        omega3++;
                    }
                    if (getArguments().getString("checked").contains("9")) {
                        probiotics++;
                        calcium++;
                    }
                    if (getArguments().getString("checked").contains("10")) {
                        roughage++;
                        protein++;
                    }
                    if (getArguments().getString("checked").contains("11")) {
                        calcium++;
                    }
                    if (getArguments().getString("checked").contains("12")) {
                        vitaminb++;
                        coq10++;
                    }
                    if (getArguments().getString("checked").contains("13")) {
                        vitaminb++;
                        coq10++;
                    }
                    if (getArguments().getString("checked").contains("14")) {
                        arginine++;
                    }
                }


            }
        }
        if (category.getHealth() != null) {
            if (category.getHealth().equals(result)) {
                if (getArguments().getString("checked").contains("0")) {
                    vitaminb++;
                    mvitamin++;
                }
                if (getArguments().getString("checked").contains("1")) {
                    vitaminb++;
                    mvitamin++;
                    omega3++;
                }
                if (getArguments().getString("checked").contains("2")) {
                    vitaminc++;
                    mvitamin++;
                }
                if (getArguments().getString("checked").contains("3")) {
                    vitamind++;
                    mvitamin++;
                    omega3++;
                }
                if (getArguments().getString("checked").contains("4")) {
                    vitamina++;
                    mvitamin++;
                    omega3++;
                }
                if (getArguments().getString("checked").contains("5")) {
                    vitamine++;
                    mvitamin++;
                }
                if (getArguments().getString("checked").contains("6")) {
                    vitamink++;
                    mvitamin++;
                }
                if (getArguments().getString("checked").contains("7")) {
                    vitaminb++;
                    mvitamin++;
                }
                if (getArguments().getString("checked").contains("8")) {
                    vitaminb++;
                    mvitamin++;
                }
                if (getArguments().getString("checked").contains("9")) {
                    probiotics++;
                }
                if (getArguments().getString("checked").contains("10")) {
                    propolis++;
                    red_ginseng++;
                }
                if (getArguments().getString("checked").contains("11")) {
                    omega3++;
                    red_ginseng++;
                }
                if (getArguments().getString("checked").contains("12")) {
                    propolis++;
                }
                if (getArguments().getString("checked").contains("13")) {
                    lutein++;
                }
            }
        }
        if (category.getBulkup() != null) {
            if (category.getBulkup().equals(result)) {
                if (getArguments().getString("checked").contains("0")) {
                    protein++;
                    creatine++;
                    bcaa++;
                    arginine++;
                    hmb++;
                    l_glutamine++;
                }
                if (getArguments().getString("checked").contains("1")) {
                    creatine++;
                    hmb++;
                    l_glutamine++;
                }
                if (getArguments().getString("checked").contains("2")) {
                    creatine++;
                }
                if (getArguments().getString("checked").contains("3")) {
                    bcaa++;
                    beta_alanine++;
                    hmb++;
                }
                if (getArguments().getString("checked").contains("4")) {
                    arginine++;
                }
                if (getArguments().getString("checked").contains("5")) {
                    protein++;
                    bcaa++;
                    arginine++;
                    l_glutamine++;
                }
                if (getArguments().getString("checked").contains("6")) {
                    protein++;
                }
                if (getArguments().getString("checked").contains("7")) {
                    creatine++;
                }
                if (getArguments().getString("checked").contains("8")) {
                    bcaa++;
                }
                if (getArguments().getString("checked").contains("9")) {
                    bcaa++;
                    beta_alanine++;
                    hmb++;
                }
                if (getArguments().getString("checked").contains("10")) {
                    beta_alanine++;
                }
                if (getArguments().getString("checked").contains("11")) {
                    beta_alanine++;
                    hmb++;
                }
            }
        }
    }

    @SuppressLint("LongLogTag")
    public void arraylistAdd(Product product, DataSnapshot snapshot, pd_classification pd_classification) {


        result = getArguments().getString("category");  //배열에 추가
        switch (result) {
            case "다이어트":     //배열에 추가
                if (omega3 > 0) {
                    if (pd_classification.getOmega3() != null) {
                        if (pd_classification.getOmega3().equals("오메가3")) {
                            productIf(omega3, product, pd_classification, snapshot);
                        }
                    }
                }
                if (probiotics > 0) {
                    if (pd_classification.getProbiotics() != null) {
                        if (pd_classification.getProbiotics().equals("프로바이오틱스")) {
                            productIf(probiotics, product, pd_classification, snapshot);
                        }
                    }
                }
                if (roughage > 0) {
                    if (pd_classification.getRoughage() != null) {
                        if (pd_classification.getRoughage().equals("식이섬유")) {
                            productIf(roughage, product, pd_classification, snapshot);
                        }
                    }
                }
                if (calcium > 0) {
                    if (pd_classification.getCalcium() != null) {
                        if (pd_classification.getCalcium().equals("칼슘")) {
                            productIf(calcium, product, pd_classification, snapshot);
                        }
                    }
                }
                if (protein > 0) {
                    if (pd_classification.getProtein() != null) {
                        if (pd_classification.getProtein().equals("단백질")) {
                            productIf(protein, product, pd_classification, snapshot);
                        }
                    }
                }
                if (vitaminb > 0) {
                    if (pd_classification.getVitaminb() != null) {
                        if (pd_classification.getVitaminb().equals("비타민b군")) {
                            productIf(vitaminb, product, pd_classification, snapshot);
                        }
                    }
                }
                if (coq10 > 0) {
                    if (pd_classification.getCoq10() != null) {
                        if (pd_classification.getCoq10().equals("코큐텐")) {
                            productIf(coq10, product, pd_classification, snapshot);
                        }
                    }
                }
                if (l_carnitine > 0) {
                    if (pd_classification.getL_carnitine() != null) {
                        if (pd_classification.getL_carnitine().equals("L_키르니틴")) {
                            productIf(l_carnitine, product, pd_classification, snapshot);
                        }
                    }
                }
                if (arginine > 0) {
                    if (pd_classification.getArginine() != null) {
                        if (pd_classification.getArginine().equals("아르기닌")) {
                            productIf(arginine, product, pd_classification, snapshot);
                        }
                    }
                }
                if (l_glutamine > 0) {
                    if (pd_classification.getL_glutamine() != null) {
                        if (pd_classification.getL_glutamine().equals("L_글루타민")) {
                            productIf(l_glutamine, product, pd_classification, snapshot);
                        }
                    }
                }

                break;

            case "건강":       //배열에 추가
                if (vitamina > 0) {
                    if (pd_classification.getVitamina() != null) {
                        if (pd_classification.getVitamina().equals("비타민a")) {
                            productIf(vitamina, product, pd_classification, snapshot);
                        }
                    }
                }
                if (vitaminb > 0) {
                    if (pd_classification.getVitaminb() != null) {
                        if (pd_classification.getVitaminb().equals("비타민b군")) {
                            productIf(vitaminb, product, pd_classification, snapshot);
                        }
                    }
                }
                if (vitaminc > 0) {
                    if (pd_classification.getVitaminc() != null) {
                        if (pd_classification.getVitaminc().equals("비타민c")) {
                            productIf(vitaminc, product, pd_classification, snapshot);
                        }
                    }
                }
                if (vitamind > 0) {
                    if (pd_classification.getVitamind() != null) {
                        if (pd_classification.getVitamind().equals("비타민d")) {
                            productIf(vitamind, product, pd_classification, snapshot);
                        }
                    }
                }
                if (vitamine > 0) {
                    if (pd_classification.getVitamine() != null) {
                        if (pd_classification.getVitamine().equals("비타민e")) {
                            productIf(vitamine, product, pd_classification, snapshot);
                        }
                    }
                }
                if (vitamink > 0) {
                    if (pd_classification.getVitamink() != null) {
                        if (pd_classification.getVitamink().equals("비타민k")) {
                            productIf(vitamink, product, pd_classification, snapshot);
                        }
                    }
                }
                if (mvitamin > 0) {
                    if (pd_classification.getMvitamin() != null) {
                        if (pd_classification.getMvitamin().equals("멀티비타민")) {
                            productIf(mvitamin, product, pd_classification, snapshot);
                        }
                    }
                }
                if (omega3 > 0) {
                    if (pd_classification.getOmega3() != null) {
                        if (pd_classification.getOmega3().equals("오메가3")) {
                            productIf(omega3, product, pd_classification, snapshot);
                        }
                    }
                }
                if (probiotics > 0) {
                    if (pd_classification.getProbiotics() != null) {
                        if (pd_classification.getProbiotics().equals("프로아이오틱스")) {
                            productIf(probiotics, product, pd_classification, snapshot);
                        }
                    }
                }
                if (propolis > 0) {
                    if (pd_classification.getPropolis() != null) {
                        if (pd_classification.getPropolis().equals("프로폴리스")) {
                            productIf(propolis, product, pd_classification, snapshot);
                        }
                    }
                }
                if (red_ginseng > 0) {
                    if (pd_classification.getRed_ginseng() != null) {
                        if (pd_classification.getRed_ginseng().equals("홍삼")) {
                            productIf(red_ginseng, product, pd_classification, snapshot);
                        }
                    }
                }
                if (lutein > 0) {
                    if (pd_classification.getLutein() != null) {
                        if (pd_classification.getLutein().equals("루테인")) {
                            productIf(lutein, product, pd_classification, snapshot);
                        }
                    }
                }

                break;

            case "벌크업":      //배열에 추가
                if (protein > 0) {
                    if (pd_classification.getProtein() != null) {
                        if (pd_classification.getProtein().equals("단백질")) {
                            productIf(protein, product, pd_classification, snapshot);
                        }
                    }
                }
                if (creatine > 0) {
                    if (pd_classification.getCalcium() != null) {
                        if (pd_classification.getCalcium().equals("크레아틴")) {
                            productIf(creatine, product, pd_classification, snapshot);
                        }
                    }
                }
                if (bcaa > 0) {
                    if (pd_classification.getBcaa() != null) {
                        if (pd_classification.getBcaa().equals("BCAA")) {
                            productIf(bcaa, product, pd_classification, snapshot);
                        }
                    }
                }
                if (arginine > 0) {
                    if (pd_classification.getArginine() != null) {
                        if (pd_classification.getArginine().equals("아르기닌")) {
                            productIf(arginine, product, pd_classification, snapshot);
                        }
                    }
                }
                if (beta_alanine > 0) {
                    if (pd_classification.getBeta_alanine() != null) {
                        if (pd_classification.getBeta_alanine().equals("베타알라닌")) {
                            productIf(beta_alanine, product, pd_classification, snapshot);
                        }
                    }
                }
                if (hmb > 0) {
                    if (pd_classification.getHmb() != null) {
                        if (pd_classification.getHmb().equals("hmb")) {
                            productIf(hmb, product, pd_classification, snapshot);
                        }
                    }
                }
                if (l_glutamine > 0) {
                    if (pd_classification.getL_glutamine() != null) {
                        if (pd_classification.getL_glutamine().equals("L_글루타민")) {
                            productIf(l_glutamine, product, pd_classification, snapshot);
                        }
                    }
                }


                break;
        }

        List<Map.Entry<String, Double>> list_resultknn = new ArrayList<Map.Entry<String, Double>>(resultknn.entrySet());
        Collections.sort(list_resultknn, new Comparator<Map.Entry<String, Double>>() {
            // compare로 값을 비교
            public int compare(Map.Entry<String, Double> obj1, Map.Entry<String, Double> obj2) {
                // 내림 차순으로 정렬

                return obj2.getValue().compareTo(obj1.getValue());
            }
        });
        arrayListSort.clear();
        for (
                int i = 0; list_resultknn.size() > i; i++) {
            for (int j = 0; arrayList.size() > j; j++) {
                if (arrayList.get(j).getPd_code().equals(list_resultknn.get(i).getKey())) {
                    arrayListSort.add(arrayList.get(j));
                    double a =list_resultknn.get(j).getValue();
                    if(arrayListSort.size()>j){
                    arrayListSort.get(j).setUs_avg( (float)a);
                    }
                }
            }
        }


    }
}
