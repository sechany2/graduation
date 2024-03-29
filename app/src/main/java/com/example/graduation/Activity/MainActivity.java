package com.example.graduation.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.graduation.Adapter.CustomAdapter;
import com.example.graduation.Fragments.FragmentProduct;
import com.example.graduation.Fragments.Fragment_notice;
import com.example.graduation.Fragments.Fragment_full_request;
import com.example.graduation.Fragments.Fragment_help;
import com.example.graduation.Fragments.Fragment_request;
import com.example.graduation.Fragments.Fragment_set;
import com.example.graduation.Fragments.Fragment_statistics;
import com.example.graduation.Fragments.Fragment_webview;
import com.example.graduation.Fragments.Fragmentadmin;
import com.example.graduation.Fragments.Fragmentcategory;
import com.example.graduation.Fragments.Fragmentmy;
import com.example.graduation.Fragments.fragment_inquiry;
import com.example.graduation.Object.Product;
import com.example.graduation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private Button btn_my, btn_survey, btn_home, btn_menu, btn_statistics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
        FrameLayout menuframe = (FrameLayout) findViewById(R.id.fg_menu);
        //액션바
        Toolbar toolbar = findViewById(R.id.next_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        ImageView a,b ;
        a =findViewById(R.id.ghealth);
        b =findViewById(R.id.han);
        a.setClipToOutline(true);
        b.setClipToOutline(true);

        //검색버튼
       /* search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString("category", "검색 결과");
                Fragmentcategory fragmentcategory = new Fragmentcategory();
                //번들 데이터 전달
                fragmentcategory.setArguments(bundle);
                replaceFragment(fragmentcategory);
                frame.removeAllViews();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }*/

        //분류 버튼 다이어트
        Button btn_diet = (Button) findViewById(R.id.btn_diet);
        btn_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 번들 프레그먼트카테고리에 데이터 전달
                Bundle bundle = new Bundle();
                bundle.putString("category", "다이어트");
                Fragmentcategory fragmentcategory = new Fragmentcategory();
                //번들 데이터 전달
                fragmentcategory.setArguments(bundle);
                replaceFragment(fragmentcategory);
                frame.removeAllViews();
            }
        });
        //분류 버튼 벌크업
        Button btn_bulkup = (Button) findViewById(R.id.btn_bulkup);
        btn_bulkup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 번들 프레그먼트카텍고리에 데이터 전달
                Bundle bundle = new Bundle();
                bundle.putString("category", "벌크업");
                Fragmentcategory fragmentcategory = new Fragmentcategory();
                //번들 데이터 전달
                fragmentcategory.setArguments(bundle);
                frame.removeAllViews();
                replaceFragment(fragmentcategory);
            }
        });
        //분류 버튼 건강
        Button btn_health = (Button) findViewById(R.id.btn_health);
        btn_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 번들 프레그먼트카텍고리에 데이터 전달
                Bundle bundle = new Bundle();
                bundle.putString("category", "건강");
                Fragmentcategory fragmentcategory = new Fragmentcategory();
                //번들 데이터 전달
                fragmentcategory.setArguments(bundle);
                frame.removeAllViews();
                replaceFragment(fragmentcategory);
            }
        });
        //하단바 마이버튼
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        String name = mAuth.getCurrentUser().getEmail();
        boolean adminCheck = name.equals("admin@a.com");
        btn_my = (Button) findViewById(R.id.btn_my);
        if (adminCheck) {
            btn_my.setText("회원 관리");
            btn_my.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drawable top_home = getResources().getDrawable(R.drawable.ic_baseline_home_24_off);
                    btn_home.setCompoundDrawablesWithIntrinsicBounds(null, top_home, null, null);
                    Drawable top_statistics = getResources().getDrawable(R.drawable.ic_baseline_bar_chart_24_off);
                    btn_statistics.setCompoundDrawablesWithIntrinsicBounds(null,top_statistics,null,null);
                    Drawable top_survey = getResources().getDrawable(R.drawable.ic_baseline_assignment_24_off);
                    btn_survey.setCompoundDrawablesWithIntrinsicBounds(null, top_survey, null, null);
                    Drawable top_my = getResources().getDrawable(R.drawable.ic_baseline_face_24);
                    btn_my.setCompoundDrawablesWithIntrinsicBounds(null, top_my, null, null);
                    btn_survey.setTextColor(Color.parseColor("#FF828282"));
                    btn_statistics.setTextColor(Color.parseColor("#FF828282"));//#DDF32424
                    btn_my.setTextColor(Color.parseColor("#DDF32424"));
                    btn_home.setTextColor(Color.parseColor("#FF828282"));
                    frame.removeAllViews();
                    replaceFragment(Fragmentadmin.newInstance());
                }
            });
        } else {

            btn_my.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drawable top_statistics = getResources().getDrawable(R.drawable.ic_baseline_bar_chart_24_off);
                    btn_statistics.setCompoundDrawablesWithIntrinsicBounds(null,top_statistics,null,null);

                    Drawable top_home = getResources().getDrawable(R.drawable.ic_baseline_home_24_off);
                    btn_home.setCompoundDrawablesWithIntrinsicBounds(null, top_home, null, null);
                   Drawable top_survey = getResources().getDrawable(R.drawable.ic_baseline_assignment_24_off);
                    btn_survey.setCompoundDrawablesWithIntrinsicBounds(null, top_survey, null, null);
                    Drawable top_my = getResources().getDrawable(R.drawable.ic_baseline_face_24);
                    btn_my.setCompoundDrawablesWithIntrinsicBounds(null, top_my, null, null);
                    btn_survey.setTextColor(Color.parseColor("#FF828282"));
                    btn_statistics.setTextColor(Color.parseColor("#FF828282"));//#DDF32424
                    btn_my.setTextColor(Color.parseColor("#DDF32424"));
                    btn_home.setTextColor(Color.parseColor("#FF828282"));
                    frame.removeAllViews();
                    replaceFragment(Fragmentmy.newInstance());
                }
            });
        }
        //하단바 설문버튼
        btn_survey = (Button) findViewById(R.id.btn_survey);
        btn_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable top_statistics = getResources().getDrawable(R.drawable.ic_baseline_bar_chart_24_off);
                btn_statistics.setCompoundDrawablesWithIntrinsicBounds(null,top_statistics,null,null);

                Drawable top_home = getResources().getDrawable(R.drawable.ic_baseline_home_24_off);
                btn_home.setCompoundDrawablesWithIntrinsicBounds(null, top_home, null, null);
                Drawable top_survey = getResources().getDrawable(R.drawable.ic_baseline_assignment_24);
                btn_survey.setCompoundDrawablesWithIntrinsicBounds(null, top_survey, null, null);
                Drawable top_my = getResources().getDrawable(R.drawable.ic_baseline_face_24_off);
                btn_my.setCompoundDrawablesWithIntrinsicBounds(null, top_my, null, null);
                btn_survey.setTextColor(Color.parseColor("#DDF32424"));
                btn_statistics.setTextColor(Color.parseColor("#FF828282"));//#DDF32424
                btn_my.setTextColor(Color.parseColor("#FF828282"));
                btn_home.setTextColor(Color.parseColor("#FF828282"));
                Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
                startActivity(intent);
            }
        });

        //하단바 홈버튼
        btn_home = (Button) findViewById(R.id.btn_home);
        LinearLayout homelayout = (LinearLayout) findViewById(R.id.homelayout);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable top_statistics = getResources().getDrawable(R.drawable.ic_baseline_bar_chart_24_off);
                btn_statistics.setCompoundDrawablesWithIntrinsicBounds(null,top_statistics,null,null);

                Drawable top_home = getResources().getDrawable(R.drawable.ic_baseline_home_24);
                btn_home.setCompoundDrawablesWithIntrinsicBounds(null, top_home, null, null);
                Drawable top_survey = getResources().getDrawable(R.drawable.ic_baseline_assignment_24_off);
                btn_survey.setCompoundDrawablesWithIntrinsicBounds(null, top_survey, null, null);
                Drawable top_my = getResources().getDrawable(R.drawable.ic_baseline_face_24_off);
                btn_my.setCompoundDrawablesWithIntrinsicBounds(null, top_my, null, null);
                btn_survey.setTextColor(Color.parseColor("#FF828282"));
                btn_statistics.setTextColor(Color.parseColor("#FF828282"));//#DDF32424
                btn_my.setTextColor(Color.parseColor("#FF828282"));
                btn_home.setTextColor(Color.parseColor("#DDF32424"));
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                frame.removeAllViews();
                frame.addView(homelayout);
            }
        });

        //통계버튼
        btn_statistics = (Button) findViewById(R.id.btn_statistics);
        if (adminCheck){
            btn_statistics.setText("통계");
            btn_statistics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drawable top_statistics = getResources().getDrawable(R.drawable.ic_baseline_bar_chart_24);
                    btn_statistics.setCompoundDrawablesWithIntrinsicBounds(null,top_statistics,null,null);
                    Drawable top_home = getResources().getDrawable(R.drawable.ic_baseline_home_24_off);
                    btn_home.setCompoundDrawablesWithIntrinsicBounds(null, top_home, null, null);
                    Drawable top_survey = getResources().getDrawable(R.drawable.ic_baseline_assignment_24_off);
                    btn_survey.setCompoundDrawablesWithIntrinsicBounds(null, top_survey, null, null);
                    Drawable top_my = getResources().getDrawable(R.drawable.ic_baseline_face_24_off);
                    btn_my.setCompoundDrawablesWithIntrinsicBounds(null, top_my, null, null);
                    btn_statistics.setTextColor(Color.parseColor("#DDF32424"));
                    btn_survey.setTextColor(Color.parseColor("#FF828282"));//#DDF32424
                    btn_my.setTextColor(Color.parseColor("#FF828282"));
                    btn_home.setTextColor(Color.parseColor("#FF828282"));
                    frame.removeAllViews();
                    replaceFragment(Fragment_webview.newInstance());
                }
            });

        }else{
            btn_statistics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drawable top_statistics = getResources().getDrawable(R.drawable.ic_baseline_bar_chart_24);
                    btn_statistics.setCompoundDrawablesWithIntrinsicBounds(null,top_statistics,null,null);

                    Drawable top_home = getResources().getDrawable(R.drawable.ic_baseline_home_24_off);
                    btn_home.setCompoundDrawablesWithIntrinsicBounds(null, top_home, null, null);
                    Drawable top_survey = getResources().getDrawable(R.drawable.ic_baseline_assignment_24_off);
                    btn_survey.setCompoundDrawablesWithIntrinsicBounds(null, top_survey, null, null);
                    Drawable top_my = getResources().getDrawable(R.drawable.ic_baseline_face_24_off);
                    btn_my.setCompoundDrawablesWithIntrinsicBounds(null, top_my, null, null);
                    btn_statistics.setTextColor(Color.parseColor("#DDF32424"));
                    btn_survey.setTextColor(Color.parseColor("#FF828282"));//#DDF32424
                    btn_my.setTextColor(Color.parseColor("#FF828282"));
                    btn_home.setTextColor(Color.parseColor("#FF828282"));
                    frame.removeAllViews();
                    replaceFragment(Fragment_statistics.newInstance());
                }
            });
        }


        //메뉴버튼
        btn_menu = (Button) findViewById(R.id.btn_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawer);

        btn_menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(drawerView);
            }
        });
        Drawable top_statistics = getResources().getDrawable(R.drawable.ic_baseline_bar_chart_24_off);
        btn_statistics.setCompoundDrawablesWithIntrinsicBounds(null,top_statistics,null,null);

        Drawable top_home = getResources().getDrawable(R.drawable.ic_baseline_home_24);
        btn_home.setCompoundDrawablesWithIntrinsicBounds(null, top_home, null, null);
        Drawable top_survey = getResources().getDrawable(R.drawable.ic_baseline_assignment_24_off);
        btn_survey.setCompoundDrawablesWithIntrinsicBounds(null, top_survey, null, null);
        Drawable top_my = getResources().getDrawable(R.drawable.ic_baseline_face_24_off);
        btn_my.setCompoundDrawablesWithIntrinsicBounds(null, top_my, null, null);
        //닫기버튼
        /*
        Button btn_close = (Button)findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
               }
        });
         */

        drawerLayout.setDrawerListener(listener);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }

        });
        TextView tv_manutitle = (TextView) findViewById(R.id.manu_title);

        //헬프 버튼
        dr_help = (View) findViewById(R.id.drawer_help);
        TextView tv_help = (TextView) findViewById(R.id.tv_help);
        tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_manutitle.setText("도움말");
                drawerLayout.closeDrawers();
                drawerLayout.openDrawer(dr_help);
                replaceMenuFragment(Fragment_help.newInstance());
            }
        });

        TextView tv_announcement = (TextView) findViewById(R.id.menutv_announcement);
        tv_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_manutitle.setText("공지사항");
                drawerLayout.closeDrawers();
                drawerLayout.openDrawer(dr_help);
                replaceMenuFragment(Fragment_notice.newInstance());
            }
        });

        TextView tv_request = (TextView) findViewById(R.id.menutv_request);
        tv_request.setText("문의 하기");
        if (adminCheck) {
            tv_request.setText("요청 확인");
        }

        tv_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_manutitle.setText("문의 하기");
                drawerLayout.closeDrawers();
                drawerLayout.openDrawer(dr_help);
                if (adminCheck) {
                    tv_manutitle.setText("요청 확인");
                    replaceMenuFragment(Fragment_full_request.newInstance());
                } else {
                    replaceMenuFragment(Fragment_request.newInstance());
                }
            }
        });

        TextView tv_set = (TextView) findViewById(R.id.menutv_set);
        tv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_manutitle.setText("설정");
                drawerLayout.closeDrawers();
                drawerLayout.openDrawer(dr_help);
                replaceMenuFragment(Fragment_set.newInstance());
            }
        });


        TextView tv_iquiry = (TextView) findViewById(R.id.menutv_inquiry);
        if (adminCheck) {tv_iquiry.setVisibility(View.GONE);}

        tv_iquiry.setOnClickListener(new View.OnClickListener() {//문의하기 버튼
            @Override
            public void onClick(View view) {
                tv_manutitle.setText("수정 요청");
                drawerLayout.closeDrawers();
                drawerLayout.openDrawer(dr_help);
                replaceMenuFragment(fragment_inquiry.newInstance());

            }
        });
        //헬프 뒤로가기 버튼
        TextView tv_back = (TextView) findViewById(R.id.tv_back);
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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    Product product = snapshot.getValue(Product.class);// 만들어뒀던 Product 객체에 데이터를 담는다.
                    if(arrayList.size()<5){
                        arrayList.add(product); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                    }
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
                new CustomAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        frame.removeAllViews();
                        ArrayList<String> pdinfo = new ArrayList<>();
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
                        pdinfo.add(arrayList.get(pos).getPd_code());             // 10번 제품코드
                        pdinfo.add(arrayList.get(pos).getPd_protein());          //11번 단백질
                        pdinfo.add(arrayList.get(pos).getPd_carbohydrate());     //12번 탄수화물
                        pdinfo.add(arrayList.get(pos).getPd_province());         //13번 지방
                        pdinfo.add(arrayList.get(pos).getPd_salt());             //14번 나트륨


                        info.putStringArrayList("product", pdinfo);
                        FragmentProduct fragmentProduct = new FragmentProduct();
                        fragmentProduct.setArguments(info);
                        replaceFragment(fragmentProduct);
                    }
                }
        );
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결


    }

    // 웹 페이지 띄우기
    public void onghealthClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.g-health.kr/portal/bbs/selectBoardList.do?bbsId=U00190&menuNo=200462"));
        startActivity(intent);
    }

    public void onhanClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hani.co.kr/arti/society/health/home01.html"));
        startActivity(intent);
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

    public void replaceMenuFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fg_menu, fragment).commit();
    }


    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        //메뉴오픈시
        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
        }

        //메뉴닫았을때
        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {
        }
    };


}

