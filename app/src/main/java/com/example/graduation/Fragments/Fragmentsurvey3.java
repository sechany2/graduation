package com.example.graduation.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Activity.MainActivity;
import com.example.graduation.Adapter.Fg3Adapter;
import com.example.graduation.Logic.PearsonCorrelation;
import com.example.graduation.Object.Product;
import com.example.graduation.Object.UserAccount;
import com.example.graduation.Object.category;
import com.example.graduation.Object.pd_classification;
import com.example.graduation.R;
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
    private Fg3Adapter adapter;
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
    private String userToken;
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
            userToken = mAuth.getUid();
            ValueEventListener uavalueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //???????????????????????? ?????? ?????? ??????
                    for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                        UserAccount userAccount = snapshot2.getValue(UserAccount.class);
                        if (snapshot2.getKey().equals(userToken)) {
                            name = userAccount.getName();       //?????? ??????
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
                public void onDataChange(@NonNull DataSnapshot dsnapshot) { //???????????????????????? ???????????? ??????
                    user = new HashMap<String, HashMap>();
                    for (DataSnapshot snapshot3 : dsnapshot.getChildren()) {
                        userReview = new HashMap<String,Double>();
                        for(DataSnapshot snapshot4 : snapshot3.getChildren()) {
                            String pdcode = snapshot4.getKey();
                            double rate = snapshot4.child("rate").getValue(Double.class);
                            userReview.put(pdcode, rate);

                        }

                        if (userReview != null) {
                            user.put(snapshot3.getKey(), userReview);
                        }
                    }
                    Log.e("user",user.toString());
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



                    resultknn = new PearsonCorrelation().knn(name, user, productList, 5);  //???????????? ?????????


                    arrayList.clear(); // ?????? ?????????????????? ?????????????????? ?????????


                    for (DataSnapshot snapshot4 : datasnapshot.getChildren()) {
                        Product product = snapshot4.getValue(Product.class);

                        category category = snapshot4.child("category").getValue(category.class);
                        pd_classification pd_classification = snapshot4.child("pd_classification").getValue(pd_classification.class);

                        categoryClassification(category);//???????????? ??? ??????

                        arraylistAdd(product, snapshot4, pd_classification);//????????? ??????

                        //adapter.notifyDataSetChanged();//?????????????????? ????????????
                        // databaseReference.removeEventListener(this);

                    }
                    ValueEventListener rvvalueEventListener2 = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            for (int i = 0; i < arrayListSort.size(); i++) {
                                // ????????? ????????? ???????
                                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                                    //MyFiles filename = (MyFiles) fileSnapshot.getValue(MyFiles.class);
                                    //??????????????? value??? ????????? ??????????????????
                                    if (fileSnapshot.child(arrayListSort.get(i).getPd_code()).child("rate").getValue(Double.class) != null) {
                                        String aaa = fileSnapshot.child(arrayListSort.get(i).getPd_code()).child("rate").getValue(Double.class).toString();
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
                            adapter.notifyDataSetChanged();//?????????????????? ????????????
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



            databaseReference.removeEventListener(uavalueEventListener);


            databaseReference.removeEventListener(pdvalueEventListener);
            adapter = new Fg3Adapter(arrayListSort, getContext());

            adapter.setOnItemClickListener(new Fg3Adapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    ImageButton favoritebtn= v.findViewById(R.id.favoritebtn);
                    favoritebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(favoritebtn.isSelected()) {
                                favoritebtn.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                                databaseReference.child("graduation").child("UserAccount").child(userToken).child("love").child(arrayListSort.get(pos).getPd_code()).setValue(null);

                            } else {
                                favoritebtn.setImageResource(R.drawable.ic_baseline_favorite_24);
                                databaseReference.child("graduation").child("UserAccount").child(userToken).child("love").child(arrayListSort.get(pos).getPd_code()).setValue("love");
                            }
                            favoritebtn.setSelected(!favoritebtn.isSelected());
                        }
                    });

                }
            });

            recyclerView.setAdapter(adapter);  //?????????????????? ??????
        } else {
            Log.e("error", "error");
        }


        Button btn_check = (Button)view.findViewById(R.id.check);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


    public HashMap<String, Double> paramMap(Object object) {

        HashMap<String, Double> hashmap = new HashMap<String, Double>();

        try {

            JSONObject json = new JSONObject(object.toString());
            Iterator i = json.keys(); // json key ???????????????

            while (i.hasNext()) {

                String k = i.next().toString(); // key ??????????????? ??????

                hashmap.put(k, Double.valueOf(json.getString(k))); // key, value??? map??? ??????
            }
        } catch (JSONException err) {
            Log.e("Exception : ", err.toString());
        }// ????????? string??? json ????????? ??????


        return hashmap;
    }

    public void productIf(int classifcation, Product product, pd_classification pd_classification, DataSnapshot snapshot) {
        boolean flagboolean = true;

        if (!(getArguments().getString("pregnant").equals(flagBundle))) {
            if (product.getIftkn_atnt_matr_cn() != null) {
                if (product.getIftkn_atnt_matr_cn().contains("?????????")) {

                    flagboolean = false;
                }
            }
        }
        if (!(getArguments().getString("baby").equals(flagBundle))) {
            if (product.getIftkn_atnt_matr_cn() != null) {
                if (product.getIftkn_atnt_matr_cn().contains("??????")) {

                    flagboolean = false;
                }
            }
        }
        if (!(getArguments().getString("allergy").equals(flagBundle))) {
            if (product.getIftkn_atnt_matr_cn() != null) {
                if (getArguments().getString("allergy").equals("0")) {
                    if (product.getIftkn_atnt_matr_cn().contains("??????")) {

                        flagboolean = false;
                    }
                }
                if (getArguments().getString("allergy").equals("1")) {
                    if (product.getIftkn_atnt_matr_cn().contains("???")) {

                        flagboolean = false;
                    }
                }
                if (getArguments().getString("allergy").equals("2")) {
                    if (product.getIftkn_atnt_matr_cn().contains("???")) {

                        flagboolean = false;
                    }
                }
                if (getArguments().getString("allergy").equals("3")) {
                    if (product.getIftkn_atnt_matr_cn().contains("??????")) {

                        flagboolean = false;
                    }
                }
                if (getArguments().getString("allergy").equals("4")) {
                    if (product.getIftkn_atnt_matr_cn().contains("????????????")) {

                        flagboolean = false;
                    }
                }

            }
        }
        if (!(getArguments().getString("disease").equals(flagBundle))) {
            if (product.getIftkn_atnt_matr_cn() != null) {
                Log.e(getArguments().getString("disease"), "");
                if (getArguments().getString("disease").equals("0")) {
                    if (product.getIftkn_atnt_matr_cn().contains("??????")) {
                        flagboolean = false;
                    }
                }
                if (getArguments().getString("disease").equals("1")) {
                    if (product.getIftkn_atnt_matr_cn().contains("??????")) {
                        flagboolean = false;
                    }
                }
                if (getArguments().getString("disease").equals("2")) {
                    if (product.getIftkn_atnt_matr_cn().contains("???????????????")) {
                        flagboolean = false;
                    }
                }
                if (getArguments().getString("disease").equals("3")) {
                    if (product.getIftkn_atnt_matr_cn().contains("??????????????????")) {
                        flagboolean = false;
                    }
                }
                if (getArguments().getString("disease").equals("4")) {
                    if (product.getIftkn_atnt_matr_cn().contains("???????????????")) {
                        flagboolean = false;
                    }
                }
                if (getArguments().getString("disease").equals("5")) {
                    if (product.getIftkn_atnt_matr_cn().contains("??????")) {
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


        result = getArguments().getString("category");  //????????? ??????
        switch (result) {
            case "????????????":     //????????? ??????
                if (omega3 > 0) {
                    if (pd_classification.getOmega3() != null) {
                        if (pd_classification.getOmega3().equals("?????????3")) {
                            productIf(omega3, product, pd_classification, snapshot);
                        }
                    }
                }
                if (probiotics > 0) {
                    if (pd_classification.getProbiotics() != null) {
                        if (pd_classification.getProbiotics().equals("?????????????????????")) {
                            productIf(probiotics, product, pd_classification, snapshot);
                        }
                    }
                }
                if (roughage > 0) {
                    if (pd_classification.getRoughage() != null) {
                        if (pd_classification.getRoughage().equals("????????????")) {
                            productIf(roughage, product, pd_classification, snapshot);
                        }
                    }
                }
                if (calcium > 0) {
                    if (pd_classification.getCalcium() != null) {
                        if (pd_classification.getCalcium().equals("??????")) {
                            productIf(calcium, product, pd_classification, snapshot);
                        }
                    }
                }
                if (protein > 0) {
                    if (pd_classification.getProtein() != null) {
                        if (pd_classification.getProtein().equals("?????????")) {
                            productIf(protein, product, pd_classification, snapshot);
                        }
                    }
                }
                if (vitaminb > 0) {
                    if (pd_classification.getVitaminb() != null) {
                        if (pd_classification.getVitaminb().equals("?????????b???")) {
                            productIf(vitaminb, product, pd_classification, snapshot);
                        }
                    }
                }
                if (coq10 > 0) {
                    if (pd_classification.getCoq10() != null) {
                        if (pd_classification.getCoq10().equals("?????????")) {
                            productIf(coq10, product, pd_classification, snapshot);
                        }
                    }
                }
                if (l_carnitine > 0) {
                    if (pd_classification.getL_carnitine() != null) {
                        if (pd_classification.getL_carnitine().equals("L_????????????")) {
                            productIf(l_carnitine, product, pd_classification, snapshot);
                        }
                    }
                }
                if (arginine > 0) {
                    if (pd_classification.getArginine() != null) {
                        if (pd_classification.getArginine().equals("????????????")) {
                            productIf(arginine, product, pd_classification, snapshot);
                        }
                    }
                }
                if (l_glutamine > 0) {
                    if (pd_classification.getL_glutamine() != null) {
                        if (pd_classification.getL_glutamine().equals("L_????????????")) {
                            productIf(l_glutamine, product, pd_classification, snapshot);
                        }
                    }
                }

                break;

            case "??????":       //????????? ??????
                if (vitamina > 0) {
                    if (pd_classification.getVitamina() != null) {
                        if (pd_classification.getVitamina().equals("?????????a")) {
                            productIf(vitamina, product, pd_classification, snapshot);
                        }
                    }
                }
                if (vitaminb > 0) {
                    if (pd_classification.getVitaminb() != null) {
                        if (pd_classification.getVitaminb().equals("?????????b???")) {
                            productIf(vitaminb, product, pd_classification, snapshot);
                        }
                    }
                }
                if (vitaminc > 0) {
                    if (pd_classification.getVitaminc() != null) {
                        if (pd_classification.getVitaminc().equals("?????????c")) {
                            productIf(vitaminc, product, pd_classification, snapshot);
                        }
                    }
                }
                if (vitamind > 0) {
                    if (pd_classification.getVitamind() != null) {
                        if (pd_classification.getVitamind().equals("?????????d")) {
                            productIf(vitamind, product, pd_classification, snapshot);
                        }
                    }
                }
                if (vitamine > 0) {
                    if (pd_classification.getVitamine() != null) {
                        if (pd_classification.getVitamine().equals("?????????e")) {
                            productIf(vitamine, product, pd_classification, snapshot);
                        }
                    }
                }
                if (vitamink > 0) {
                    if (pd_classification.getVitamink() != null) {
                        if (pd_classification.getVitamink().equals("?????????k")) {
                            productIf(vitamink, product, pd_classification, snapshot);
                        }
                    }
                }
                if (mvitamin > 0) {
                    if (pd_classification.getMvitamin() != null) {
                        if (pd_classification.getMvitamin().equals("???????????????")) {
                            productIf(mvitamin, product, pd_classification, snapshot);
                        }
                    }
                }
                if (omega3 > 0) {
                    if (pd_classification.getOmega3() != null) {
                        if (pd_classification.getOmega3().equals("?????????3")) {
                            productIf(omega3, product, pd_classification, snapshot);
                        }
                    }
                }
                if (probiotics > 0) {
                    if (pd_classification.getProbiotics() != null) {
                        if (pd_classification.getProbiotics().equals("?????????????????????")) {
                            productIf(probiotics, product, pd_classification, snapshot);
                        }
                    }
                }
                if (propolis > 0) {
                    if (pd_classification.getPropolis() != null) {
                        if (pd_classification.getPropolis().equals("???????????????")) {
                            productIf(propolis, product, pd_classification, snapshot);
                        }
                    }
                }
                if (red_ginseng > 0) {
                    if (pd_classification.getRed_ginseng() != null) {
                        if (pd_classification.getRed_ginseng().equals("??????")) {
                            productIf(red_ginseng, product, pd_classification, snapshot);
                        }
                    }
                }
                if (lutein > 0) {
                    if (pd_classification.getLutein() != null) {
                        if (pd_classification.getLutein().equals("?????????")) {
                            productIf(lutein, product, pd_classification, snapshot);
                        }
                    }
                }

                break;

            case "?????????":      //????????? ??????
                if (protein > 0) {
                    if (pd_classification.getProtein() != null) {
                        if (pd_classification.getProtein().equals("?????????")) {
                            productIf(protein, product, pd_classification, snapshot);
                        }
                    }
                }
                if (creatine > 0) {
                    if (pd_classification.getCalcium() != null) {
                        if (pd_classification.getCalcium().equals("????????????")) {
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
                        if (pd_classification.getArginine().equals("????????????")) {
                            productIf(arginine, product, pd_classification, snapshot);
                        }
                    }
                }
                if (beta_alanine > 0) {
                    if (pd_classification.getBeta_alanine() != null) {
                        if (pd_classification.getBeta_alanine().equals("???????????????")) {
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
                        if (pd_classification.getL_glutamine().equals("L_????????????")) {
                            productIf(l_glutamine, product, pd_classification, snapshot);
                        }
                    }
                }


                break;
        }

        List<Map.Entry<String, Double>> list_resultknn = new ArrayList<Map.Entry<String, Double>>(resultknn.entrySet());
        Collections.sort(list_resultknn, new Comparator<Map.Entry<String, Double>>() {
            // compare??? ?????? ??????
            public int compare(Map.Entry<String, Double> obj1, Map.Entry<String, Double> obj2) {
                // ?????? ???????????? ??????

                return obj2.getValue().compareTo(obj1.getValue());
            }
        });
        arrayListSort.clear();
        for (
                int i = 0; list_resultknn.size() > i; i++) {

            for (int j = 0; arrayList.size() > j; j++) {
                if (arrayList.get(j).getPd_code().equals(list_resultknn.get(i).getKey())) {
                    arrayListSort.add(arrayList.get(j));



                }
            }
        }
        for (int k =0; arrayListSort.size() > k ; k++){
            double a = list_resultknn.get(k).getValue();
            arrayListSort.get(k).setUs_avg((float) a);
        }

    }
}
