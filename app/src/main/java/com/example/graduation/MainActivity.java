package com.example.graduation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    Button btn_menu, btn_survey, btn_home, btn_my; //하단바 버튼
    Button btn_diet, btn_bulkup, btn_health; //분류 버튼
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


        //분류 버튼 다이어트
        Button btn_diet = (Button)findViewById(R.id.btn_diet);
        btn_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                // 위 코드가 만약 Listener 밖에 있어서 화면이 사용자에게 보여진 후 1회만 실행 된다면,
                // 한번의 Fragment의 변화를 Commit()한 후 다시 다른 화면으로 Commit하려할 때 아래와 같은 에러가 발생한다.
                // 이미 화면을 그리면서 Commit()한 transaction에 다른 Fragment를 할당하고 Commit()하려 했기 때문에 이미 commit되었다고 에러가 발생하면서 앱이 꺼지는 것이다.
                Fragmentcategory fragmentcategory = new Fragmentcategory();
                frame.removeAllViews();
                transaction.replace(R.id.frame, fragmentcategory);
                transaction.commit();
            }
        });
        //하단바 마이버튼
        Button btn_my = (Button)findViewById(R.id.btn_my);
        btn_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragmentmy fragmentmy = new Fragmentmy();
                frame.removeAllViews();
                transaction.replace(R.id.frame, fragmentmy);
                transaction.commit();
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
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawer);
        //Query a = databaseReference.child("product_01").toString();
        //메뉴
        Button btn_menu = (Button)findViewById(R.id.btn_menu);      //메뉴버튼
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               drawerLayout.openDrawer(drawerView);
            }
        });

        Button btn_close = (Button)findViewById(R.id.btn_close);    //닫기버튼

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
                    Product product = snapshot.getValue(Product.class); // 만들어뒀던 Product 객체에 데이터를 담는다.
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
                        replaceFragment(FragmentProduct.newInstance());


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
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) { }
            //메뉴오픈시
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {  }
            //닫았을때
            @Override
            public void onDrawerClosed(@NonNull View drawerView) {  }
            @Override
            public void onDrawerStateChanged(int newState) { }
        };
    }

