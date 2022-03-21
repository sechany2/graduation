package com.example.graduation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private View dr_help;
    private View drawerView;
    //Button btn_menu, btn_survey, btn_home, btn_my; //하단바 버튼
    //Button btn_diet, btn_bulkup, btn_health; //분류 버튼
    //메인화면 리사이클러뷰
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout frame =(FrameLayout)findViewById(R.id.frame);

        //액션바
        Toolbar toolbar =findViewById(R.id.next_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("graduation");


        //분류 버튼 다이어트
        Button btn_diet = (Button)findViewById(R.id.btn_diet);
        btn_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 번들 프레그먼트카테고리에 데이터 전달
                Bundle bundle = new Bundle();
                bundle.putString("category","다이어트");
                Fragmentcategory fragmentcategory = new Fragmentcategory();
                //번들 데이터 전달
                fragmentcategory.setArguments(bundle);
                replaceFragment(fragmentcategory);
                frame.removeAllViews();
            }
        });
        //분류 버튼 벌크업
        Button btn_bulkup = (Button)findViewById(R.id.btn_bulkup);
        btn_bulkup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 번들 프레그먼트카텍고리에 데이터 전달
                Bundle bundle = new Bundle();
                bundle.putString("category","벌크업");
                Fragmentcategory fragmentcategory = new Fragmentcategory();
                //번들 데이터 전달
                fragmentcategory.setArguments(bundle);
                frame.removeAllViews();
                replaceFragment(fragmentcategory);
            }
        });
        //분류 버튼 건강
        Button btn_health = (Button)findViewById(R.id.btn_health);
        btn_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 번들 프레그먼트카텍고리에 데이터 전달
                Bundle bundle = new Bundle();
                bundle.putString("category","건강");
                Fragmentcategory fragmentcategory = new Fragmentcategory();
                //번들 데이터 전달
                fragmentcategory.setArguments(bundle);
                frame.removeAllViews();
                replaceFragment(fragmentcategory);
            }
        });
        //하단바 마이버튼
        Button btn_my = (Button)findViewById(R.id.btn_my);
        btn_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame.removeAllViews();
                replaceFragment(Fragmentmy.newInstance());
            }
        });

        //하단바 설문버튼
        Button btn_survey = (Button)findViewById(R.id.btn_survey);
        btn_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
                startActivity(intent);
            }
        });

        //하단바 홈버튼
        Button btn_home = (Button)findViewById(R.id.btn_home);
        LinearLayout homelayout = (LinearLayout)findViewById(R.id.homelayout);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                frame.removeAllViews();
                frame.addView(homelayout);
            }
        });

        //메뉴버튼
        Button btn_menu = (Button)findViewById(R.id.btn_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawer);

        btn_menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(drawerView);
            }
        });

        //닫기버튼
        Button btn_close = (Button)findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
            }
        });

        drawerLayout.setDrawerListener(listener);

        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) { return true;}
        });
        //헬프 버튼
        dr_help = (View) findViewById(R.id.drawer_help);
        TextView tv_help = (TextView) findViewById(R.id.tv_help);
        tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                drawerLayout.closeDrawers();
                drawerLayout.openDrawer(dr_help);

            }
        });
        //헬프 뒤로가기 버튼
        TextView tv_back = (TextView)findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                drawerLayout.openDrawer(drawerView);

            }
        });
        //메인화면 리사이클러뷰
        recyclerView = findViewById(R.id.realtimeview); //아디 연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // 객체를 담을 어레이 리스트(어댑터쪽으로)

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동

        databaseReference = database.getReference("Product"); // DB테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){ // 반복문으로 데이터 List를 추출해냄
                    Product product = snapshot.getValue(Product.class);// 만들어뒀던 Product 객체에 데이터를 담는다.
                    arrayList.add(product); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB를 가져오던중 에러 발생 시
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        adapter = new CustomAdapter(arrayList, this);
        adapter.setOnItemClickListener(
                new DietAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        frame.removeAllViews();
                        ArrayList<String> pdinfo= new ArrayList<>();
                        Bundle info = new Bundle();//제품정보 보낼 번들 info 생성
                        pdinfo.add(arrayList.get(pos).getPd_name());             // 0번 이름
                        pdinfo.add(arrayList.get(pos).getPd_brandname());        // 1번 브랜드이름
                        pdinfo.add(arrayList.get(pos).getPd_profile());          // 2번 이미지
                        pdinfo.add(arrayList.get(pos).getPrimary_fnclty());      // 3번 주된기능성
                        pdinfo.add(arrayList.get(pos).getNtk_mthd());            // 4번 섭취방법
                        pdinfo.add(arrayList.get(pos).getIndiv_rawmtrl_nm());    // 5번 기능성원료
                        pdinfo.add(arrayList.get(pos).getEtc_rawmtrl_nm());      // 6번 기타원료
                        pdinfo.add(arrayList.get(pos).getCap_rawmtrl_nm());      // 7번 캡슐 원료
                        pdinfo.add(arrayList.get(pos).getIftkn_atnt_matr_cn());  // 8번 주의사항
                        pdinfo.add(arrayList.get(pos).getPrdt_shap_cd_nm());     // 9번 제품형태
                        info.putStringArrayList("product",pdinfo);
                        FragmentProduct fragmentProduct = new FragmentProduct();
                        fragmentProduct.setArguments(info);
                        replaceFragment(fragmentProduct);
                    }
                }
        );
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결


    }

    //프래그먼트 이동 메소드
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).commit();
    }

    public void replaceframeProduct(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameProduct, fragment).commit();
    }


        DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }
            //메뉴오픈시
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {  }
            //메뉴닫았을때
            @Override
            public void onDrawerClosed(@NonNull View drawerView) {  }
            @Override
            public void onDrawerStateChanged(int newState) { }
        };


    }

