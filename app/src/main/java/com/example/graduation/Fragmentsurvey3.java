package com.example.graduation;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;
import java.util.Map;


public class Fragmentsurvey3 extends Fragment {


    public Fragmentsurvey3() {
    }

    private RecyclerView recyclerView;
    private ArrayList<String> productList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String result, name;
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

            databaseReference.child("graduation").child("UserAccount").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {  //파이어베이스에서 본인 정보 저장
                    for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                        UserAccount userAccount = snapshot2.getValue(UserAccount.class);
                        if (snapshot2.getKey().equals(mAuth.getUid())) {
                            name = userAccount.getName();       //이름 저장
                        }
                    }
                    //databaseReference.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("error", error.toString());
                }
            });
            user = new HashMap<String, HashMap>();
            databaseReference.child("Review").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dsnapshot) { //파이어베이스에서 리뷰정보 저장
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
            });


            databaseReference.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    productList.clear();
                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {

                        productList.add(snapshot.getKey());
                    }

                    resultknn = new HashMap<String, Double>();
                    resultknn = new PearsonCorrelation().knn(name, user,productList);  //상관계수 구하기
                    Log.e("knn:",resultknn.toString());
                    arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);

                        category category = snapshot.child("category").getValue(category.class);
                        pd_classification pd_classification = snapshot.child("pd_classification").getValue(pd_classification.class);

                        categoryClassification(category);//카테고리 별 분류

                        arraylistAdd(product, snapshot,pd_classification);//배열에 추가

                        adapter.notifyDataSetChanged();//리사이클러뷰 업데이트
                        // databaseReference.removeEventListener(this);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("error", error.toString());
                }
            });

            adapter = new DietAdapter(arrayList, getContext());

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

    public void arraylistAdd(Product product, DataSnapshot snapshot, pd_classification pd_classification) {

        result = getArguments().getString("category");
        //배열에 추가
        switch (result) {
            case "다이어트":     //배열에 추가

                if (omega3 > 0) {
                    if(pd_classification.getOmega3()!=null){
                        if (pd_classification.getOmega3().equals("오메가3")) {
                            if (ifknn(snapshot.getKey())) {
                                    if(!arrayList.contains(product)){
                                        arrayList.add(product);
                                    }
                            }
                        }
                    }
                }
                if (probiotics > 0) {
                    if(pd_classification.getProbiotics()!=null){
                        if (pd_classification.getProbiotics().equals("프로바이오틱스")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (roughage > 0) {
                    if(pd_classification.getRoughage()!=null){
                        if (pd_classification.getRoughage().equals("식이섬유")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (calcium > 0) {
                    if(pd_classification.getCalcium()!=null){
                        if (pd_classification.getCalcium().equals("칼슘")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (protein > 0) {
                    if(pd_classification.getProtein()!=null){
                        if (pd_classification.getProtein().equals("단백질")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (vitaminb > 0) {
                    if(pd_classification.getVitaminb()!=null){
                        if (pd_classification.getVitaminb().equals("비타민b군")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }
                }
                if (coq10 > 0) {
                    if(pd_classification.getCoq10()!=null){
                        if (pd_classification.getCoq10().equals("코큐텐")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (l_carnitine > 0) {
                    if(pd_classification.getL_carnitine()!=null){
                        if (pd_classification.getL_carnitine().equals("L_카르니틴")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (arginine > 0) {
                    if(pd_classification.getArginine()!=null){
                        if (pd_classification.getArginine().equals("아르기닌")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (l_glutamine > 0) {
                    if(pd_classification.getL_glutamine()!=null){
                        if (pd_classification.getL_glutamine().equals("L_글루타민")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }

                break;

            case "건강":       //배열에 추가

                if (vitamina > 0) {
                    if(pd_classification.getVitamina()!=null){
                        if (pd_classification.getVitamina().equals("비타민a")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (vitaminb > 0) {
                    if(pd_classification.getVitaminb()!=null){
                        if (pd_classification.getVitaminb().equals("비타민b군")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (vitaminc > 0) {
                    if(pd_classification.getVitaminc()!=null){
                        if (pd_classification.getVitaminc().equals("비타민c")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (vitamind > 0) {
                    if(pd_classification.getVitamind()!=null){
                        if (pd_classification.getVitamind().equals("비타민d")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (vitamine > 0) {
                    if(pd_classification.getVitamine()!=null){
                        if (pd_classification.getVitamine().equals("비타민e")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (vitamink > 0) {
                    if(pd_classification.getVitamink()!=null){
                        if (pd_classification.getVitamink().equals("비타민k")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (mvitamin > 0) {
                    if(pd_classification.getMvitamin()!=null){
                        if (pd_classification.getMvitamin().equals("멀티비타민")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (omega3 > 0) {
                    if(pd_classification.getOmega3()!=null){
                        if (pd_classification.getOmega3().equals("오메가3")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (probiotics > 0) {
                    if(pd_classification.getProbiotics()!=null){
                        if (pd_classification.getProbiotics().equals("프로바이오틱스")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (propolis > 0) {
                    if(pd_classification.getPropolis()!=null){
                        if (pd_classification.getPropolis().equals("프로폴리스")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (red_ginseng > 0) {
                    if(pd_classification.getRed_ginseng()!=null){
                        if (pd_classification.getRed_ginseng().equals("홍삼")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (lutein > 0) {
                    if(pd_classification.getLutein()!=null){
                        if (pd_classification.getLutein().equals("루테인")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }

                break;

            case "벌크업":      //배열에 추가


                if (protein > 0) {
                    if(pd_classification.getProtein()!=null){
                        if (pd_classification.getProtein().equals("단백질")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (creatine > 0) {
                    if(pd_classification.getCreatine()!=null){
                        if (pd_classification.getCreatine().equals("크레아틴")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (bcaa > 0) {
                    if(pd_classification.getBcaa()!=null){
                        if (pd_classification.getBcaa().equals("BCAA")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (arginine > 0) {
                    if(pd_classification.getArginine()!=null){
                        if (pd_classification.getArginine().equals("아르기닌")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (beta_alanine > 0) {
                    if(pd_classification.getBeta_alanine()!=null){
                        if (pd_classification.getBeta_alanine().equals("베타알라닌")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (hmb > 0) {
                    if(pd_classification.getHmb()!=null){
                        if (pd_classification.getHmb().equals("HMB")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }

                }
                if (l_glutamine > 0) {
                    if(pd_classification.getL_glutamine()!=null){
                        if (pd_classification.getL_glutamine().equals("L_글루타민")) {
                            if (ifknn(snapshot.getKey())) {
                                if(!arrayList.contains(product)){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }
                }
                break;
        }
    }
}
