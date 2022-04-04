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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Fragmentsurvey3 extends Fragment {
    public Fragmentsurvey3(){ }

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String result;

    private int omega3=0,probiotics=0,roughage=0,calcium=0,protein=0, vitaminb=0,coq10=0,l_carnitine=0
            ,arginine=0,l_glutamine=0,creatine=0,bcaa=0,beta_alanine=0,hmb=0
            ,vitamina=0,vitaminc=0,vitamind=0,vitamine=0,vitamink=0,mvitamin=0,
            propolis=0,red_ginseng=0,lutein=0;

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

                                //배열에 추가
                                if (omega3 > 0) {
                                    if (product.getPd_classification().equals("오메가3")) {

                                        Log.e("product1",product.toString());
                                        arrayList.add(product);

                                    }
                                }
                                if (probiotics > 0) {
                                    if (product.getPd_classification().equals("프로바이오틱스")) {
                                        //snapshot.getKey() == Product_n
                                        new PearsonCorrelation().knn2(name);
                                        arrayList.add(product);

                                    }
                                }
                                if (roughage > 0) {
                                    if (product.getPd_classification().equals("식이섬유")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (calcium > 0) {
                                    if (product.getPd_classification().equals("칼슘")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (protein > 0) {
                                    if (product.getPd_classification().equals("단백질")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (vitaminb > 0) {
                                    if (product.getPd_classification().equals("비타민b군")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (coq10 > 0) {
                                    if (product.getPd_classification().equals("코큐텐")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (l_carnitine > 0) {
                                    if (product.getPd_classification().equals("L_카르니틴")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (arginine > 0) {
                                    if (product.getPd_classification().equals("아르기닌")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (l_glutamine > 0) {
                                    if (product.getPd_classification().equals("L_글루타민")) {
                                        arrayList.add(product);
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

                                //배열에 추가
                                if (vitamina > 0) {
                                    if (product.getPd_classification().equals("비타민a")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (vitaminb > 0) {
                                    if (product.getPd_classification().equals("비타민b군")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (vitaminc > 0) {
                                    if (product.getPd_classification().equals("비타민c")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (vitamind > 0) {
                                    if (product.getPd_classification().equals("비타민d")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (vitamine > 0) {
                                    if (product.getPd_classification().equals("비타민e")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (vitamink > 0) {
                                    if (product.getPd_classification().equals("비타민k")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (mvitamin > 0) {
                                    if (product.getPd_classification().equals("멀티비타민")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (omega3 > 0) {
                                    if (product.getPd_classification().equals("오메가3")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (probiotics > 0) {
                                    if (product.getPd_classification().equals("프로바이오틱스")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (propolis > 0) {
                                    if (product.getPd_classification().equals("프로폴리스")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (red_ginseng > 0) {
                                    if (product.getPd_classification().equals("홍삼")) {
                                        arrayList.add(product);
                                    }
                                }
                                if (lutein > 0) {
                                    if (product.getPd_classification().equals("루테인")) {
                                        arrayList.add(product);
                                    }
                                }
                            }
                        }
                        if (category.getBulkup() != null) {
                            if (category.getBulkup().equals(result)) {
                                if(getArguments().getString("checked").contains("0")){
                                    protein++;
                                    creatine++;
                                    bcaa++;
                                    arginine++;
                                    hmb++;
                                    l_glutamine++;
                                }
                                if(getArguments().getString("checked").contains("1")){
                                    creatine++;
                                    hmb++;
                                    l_glutamine++;
                                }
                                if(getArguments().getString("checked").contains("2")){
                                    creatine++;
                                }
                                if(getArguments().getString("checked").contains("3")){
                                    bcaa++;
                                    beta_alanine++;
                                    hmb++;
                                }
                                if(getArguments().getString("checked").contains("4")){
                                    arginine++;
                                }
                                if(getArguments().getString("checked").contains("5")){
                                    protein++;
                                    bcaa++;
                                    arginine++;
                                    l_glutamine++;
                                }
                                if(getArguments().getString("checked").contains("6")){
                                    protein++;
                                }
                                if(getArguments().getString("checked").contains("7")){
                                    creatine++;
                                }
                                if(getArguments().getString("checked").contains("8")){
                                    bcaa++;
                                }
                                if(getArguments().getString("checked").contains("9")){
                                    bcaa++;
                                    beta_alanine++;
                                    hmb++;
                                }
                                if(getArguments().getString("checked").contains("10")){
                                    beta_alanine++;
                                }
                                if(getArguments().getString("checked").contains("11")){
                                    beta_alanine++;
                                    hmb++;
                                }

                            //배열에 추가
                            if(protein>0){
                                if(product.getPd_classification().equals("단백질")){
                                    arrayList.add(product);
                                }
                            }
                            if(creatine>0){
                                if(product.getPd_classification().equals("크레아틴")){
                                    arrayList.add(product);
                                }
                            }
                            if(bcaa>0){
                                if(product.getPd_classification().equals("BCAA")){
                                    arrayList.add(product);
                                }
                            }
                            if(arginine>0){
                                if(product.getPd_classification().equals("아르기닌")){
                                    arrayList.add(product);
                                }
                            }
                            if(beta_alanine>0){
                                if(product.getPd_classification().equals("베타알라닌")){
                                    arrayList.add(product);
                                }
                            }
                            if(hmb>0){
                                if(product.getPd_classification().equals("HMB")){
                                    arrayList.add(product);
                                }
                            }
                            if(l_glutamine>0){
                                if(product.getPd_classification().equals("L_글루타민")){
                                    arrayList.add(product);
                                }
                            }
                        }
                    }
                    }

                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("error",error.toString());
                }
            });

            adapter = new DietAdapter(arrayList, getContext());
            recyclerView.setAdapter(adapter);
        }
        else {
            Log.e("sasd","sasd");
        }
        return view;
    }


    public HashMap<String, Double> paramMap(Object object ) throws JSONException {

        HashMap<String, Double> hashmap = new HashMap<String, Double>();

        JSONObject json = new JSONObject(String.valueOf(object)); // 받아온 string을 json 으로로 변환

        Iterator i = json.keys(); // json key 요소읽어옴

        while(i.hasNext()){

            String k = i.next().toString(); // key 순차적으로 추출

            hashmap.put(k, Double.valueOf(json.getString(k))); // key, value를 map에 삽입
        }

        return hashmap;
    }





}
