package com.example.graduation.Activity;

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
import com.example.graduation.Fragments.Fragment_announcement;
import com.example.graduation.Fragments.Fragment_full_request;
import com.example.graduation.Fragments.Fragment_help;
import com.example.graduation.Fragments.Fragment_request;
import com.example.graduation.Fragments.Fragment_set;
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
    //Button btn_menu, btn_survey, btn_home, btn_my; //????????? ??????
    //Button btn_diet, btn_bulkup, btn_health; //?????? ??????
    //???????????? ??????????????????
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private Button btn_my, btn_survey, btn_home, btn_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
        FrameLayout menuframe = (FrameLayout) findViewById(R.id.fg_menu);
        //?????????
        Toolbar toolbar = findViewById(R.id.next_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        ImageView a,b ;
        a =findViewById(R.id.ghealth);
        b =findViewById(R.id.han);
        a.setClipToOutline(true);
        b.setClipToOutline(true);


        //?????? ?????? ????????????
        Button btn_diet = (Button) findViewById(R.id.btn_diet);
        btn_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ?????? ?????????????????????????????? ????????? ??????
                Bundle bundle = new Bundle();
                bundle.putString("category", "????????????");
                Fragmentcategory fragmentcategory = new Fragmentcategory();
                //?????? ????????? ??????
                fragmentcategory.setArguments(bundle);
                replaceFragment(fragmentcategory);
                frame.removeAllViews();
            }
        });
        //?????? ?????? ?????????
        Button btn_bulkup = (Button) findViewById(R.id.btn_bulkup);
        btn_bulkup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ?????? ?????????????????????????????? ????????? ??????
                Bundle bundle = new Bundle();
                bundle.putString("category", "?????????");
                Fragmentcategory fragmentcategory = new Fragmentcategory();
                //?????? ????????? ??????
                fragmentcategory.setArguments(bundle);
                frame.removeAllViews();
                replaceFragment(fragmentcategory);
            }
        });
        //?????? ?????? ??????
        Button btn_health = (Button) findViewById(R.id.btn_health);
        btn_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ?????? ?????????????????????????????? ????????? ??????
                Bundle bundle = new Bundle();
                bundle.putString("category", "??????");
                Fragmentcategory fragmentcategory = new Fragmentcategory();
                //?????? ????????? ??????
                fragmentcategory.setArguments(bundle);
                frame.removeAllViews();
                replaceFragment(fragmentcategory);
            }
        });
        //????????? ????????????
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        String name = mAuth.getCurrentUser().getEmail();
        boolean adminCheck = name.equals("admin@a.com");
        btn_my = (Button) findViewById(R.id.btn_my);
        if (adminCheck) {
            btn_my.setText("?????? ??????");
            btn_my.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drawable top_home = getResources().getDrawable(R.drawable.ic_baseline_home_24_off);
                    btn_home.setCompoundDrawablesWithIntrinsicBounds(null, top_home, null, null);
                    Drawable top_menu = getResources().getDrawable(R.drawable.ic_baseline_menu_24_off);
                    btn_menu.setCompoundDrawablesWithIntrinsicBounds(null, top_menu, null, null);
                    Drawable top_survey = getResources().getDrawable(R.drawable.ic_baseline_assignment_24_off);
                    btn_survey.setCompoundDrawablesWithIntrinsicBounds(null, top_survey, null, null);
                    Drawable top_my = getResources().getDrawable(R.drawable.ic_baseline_face_24);
                    btn_my.setCompoundDrawablesWithIntrinsicBounds(null, top_my, null, null);
                    btn_survey.setTextColor(Color.parseColor("#FF828282"));
                    btn_menu.setTextColor(Color.parseColor("#FF828282"));//#DDF32424
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
                    Drawable top_home = getResources().getDrawable(R.drawable.ic_baseline_home_24_off);
                    btn_home.setCompoundDrawablesWithIntrinsicBounds(null, top_home, null, null);
                    Drawable top_menu = getResources().getDrawable(R.drawable.ic_baseline_menu_24_off);
                    btn_menu.setCompoundDrawablesWithIntrinsicBounds(null, top_menu, null, null);
                    Drawable top_survey = getResources().getDrawable(R.drawable.ic_baseline_assignment_24_off);
                    btn_survey.setCompoundDrawablesWithIntrinsicBounds(null, top_survey, null, null);
                    Drawable top_my = getResources().getDrawable(R.drawable.ic_baseline_face_24);
                    btn_my.setCompoundDrawablesWithIntrinsicBounds(null, top_my, null, null);
                    btn_survey.setTextColor(Color.parseColor("#FF828282"));
                    btn_menu.setTextColor(Color.parseColor("#FF828282"));//#DDF32424
                    btn_my.setTextColor(Color.parseColor("#DDF32424"));
                    btn_home.setTextColor(Color.parseColor("#FF828282"));
                    frame.removeAllViews();
                    replaceFragment(Fragmentmy.newInstance());
                }
            });
        }
        //????????? ????????????
        btn_survey = (Button) findViewById(R.id.btn_survey);
        btn_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable top_home = getResources().getDrawable(R.drawable.ic_baseline_home_24_off);
                btn_home.setCompoundDrawablesWithIntrinsicBounds(null, top_home, null, null);
                Drawable top_menu = getResources().getDrawable(R.drawable.ic_baseline_menu_24_off);
                btn_menu.setCompoundDrawablesWithIntrinsicBounds(null, top_menu, null, null);
                Drawable top_survey = getResources().getDrawable(R.drawable.ic_baseline_assignment_24);
                btn_survey.setCompoundDrawablesWithIntrinsicBounds(null, top_survey, null, null);
                Drawable top_my = getResources().getDrawable(R.drawable.ic_baseline_face_24_off);
                btn_my.setCompoundDrawablesWithIntrinsicBounds(null, top_my, null, null);
                btn_survey.setTextColor(Color.parseColor("#DDF32424"));
                btn_menu.setTextColor(Color.parseColor("#FF828282"));//#DDF32424
                btn_my.setTextColor(Color.parseColor("#FF828282"));
                btn_home.setTextColor(Color.parseColor("#FF828282"));
                Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
                startActivity(intent);
            }
        });

        //????????? ?????????
        btn_home = (Button) findViewById(R.id.btn_home);
        LinearLayout homelayout = (LinearLayout) findViewById(R.id.homelayout);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable top_home = getResources().getDrawable(R.drawable.ic_baseline_home_24);
                btn_home.setCompoundDrawablesWithIntrinsicBounds(null, top_home, null, null);
                Drawable top_menu = getResources().getDrawable(R.drawable.ic_baseline_menu_24_off);
                btn_menu.setCompoundDrawablesWithIntrinsicBounds(null, top_menu, null, null);
                Drawable top_survey = getResources().getDrawable(R.drawable.ic_baseline_assignment_24_off);
                btn_survey.setCompoundDrawablesWithIntrinsicBounds(null, top_survey, null, null);
                Drawable top_my = getResources().getDrawable(R.drawable.ic_baseline_face_24_off);
                btn_my.setCompoundDrawablesWithIntrinsicBounds(null, top_my, null, null);
                btn_survey.setTextColor(Color.parseColor("#FF828282"));
                btn_menu.setTextColor(Color.parseColor("#FF828282"));//#DDF32424
                btn_my.setTextColor(Color.parseColor("#FF828282"));
                btn_home.setTextColor(Color.parseColor("#DDF32424"));
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                frame.removeAllViews();
                frame.addView(homelayout);
            }
        });

        //????????????
        btn_menu = (Button) findViewById(R.id.btn_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawer);

        btn_menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(drawerView);
            }
        });
        Drawable top_home = getResources().getDrawable(R.drawable.ic_baseline_home_24);
        btn_home.setCompoundDrawablesWithIntrinsicBounds(null, top_home, null, null);
        Drawable top_menu = getResources().getDrawable(R.drawable.ic_baseline_menu_24_off);
        btn_menu.setCompoundDrawablesWithIntrinsicBounds(null, top_menu, null, null);
        Drawable top_survey = getResources().getDrawable(R.drawable.ic_baseline_assignment_24_off);
        btn_survey.setCompoundDrawablesWithIntrinsicBounds(null, top_survey, null, null);
        Drawable top_my = getResources().getDrawable(R.drawable.ic_baseline_face_24_off);
        btn_my.setCompoundDrawablesWithIntrinsicBounds(null, top_my, null, null);
        //????????????
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

        //?????? ??????
        dr_help = (View) findViewById(R.id.drawer_help);
        TextView tv_help = (TextView) findViewById(R.id.tv_help);
        tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_manutitle.setText("?????????");
                drawerLayout.closeDrawers();
                drawerLayout.openDrawer(dr_help);
                replaceMenuFragment(Fragment_help.newInstance());
            }
        });

        TextView tv_announcement = (TextView) findViewById(R.id.menutv_announcement);
        tv_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_manutitle.setText("????????????");
                drawerLayout.closeDrawers();
                drawerLayout.openDrawer(dr_help);
                replaceMenuFragment(Fragment_announcement.newInstance());
            }
        });

        TextView tv_request = (TextView) findViewById(R.id.menutv_request);
        tv_request.setText("?????? ??????");
        tv_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_manutitle.setText("?????? ??????");
                drawerLayout.closeDrawers();
                drawerLayout.openDrawer(dr_help);
                if (adminCheck) {
                    tv_manutitle.setText("?????? ??????");
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
                tv_manutitle.setText("??????");
                drawerLayout.closeDrawers();
                drawerLayout.openDrawer(dr_help);
                replaceMenuFragment(Fragment_set.newInstance());
            }
        });


        TextView tv_iquiry = (TextView) findViewById(R.id.menutv_inquiry);
        if (adminCheck) {tv_iquiry.setVisibility(View.GONE);}

        tv_iquiry.setOnClickListener(new View.OnClickListener() {//???????????? ??????
            @Override
            public void onClick(View view) {
                tv_manutitle.setText("?????? ??????");
                drawerLayout.closeDrawers();
                drawerLayout.openDrawer(dr_help);
                replaceMenuFragment(fragment_inquiry.newInstance());

            }
        });
        //?????? ???????????? ??????
        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                drawerLayout.openDrawer(drawerView);

            }
        });




        //???????????? ??????????????????
        recyclerView = findViewById(R.id.realtimeview); //?????? ??????
        recyclerView.setHasFixedSize(true); //?????????????????? ???????????? ??????
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // ????????? ?????? ????????? ?????????(??????????????????)

        database = FirebaseDatabase.getInstance(); // ?????????????????? ?????????????????? ??????

        databaseReference = database.getReference("Product"); // DB????????? ??????
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //?????????????????? ????????????????????? ???????????? ???????????? ???
                arrayList.clear(); // ?????? ?????????????????? ?????????????????? ?????????
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // ??????????????? ????????? List??? ????????????
                    Product product = snapshot.getValue(Product.class);// ??????????????? Product ????????? ???????????? ?????????.
                    if(arrayList.size()<5){
                        arrayList.add(product); // ?????? ??????????????? ?????????????????? ?????? ????????????????????? ?????? ??????
                    }
                }
                adapter.notifyDataSetChanged(); // ????????? ?????? ??? ????????????
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB??? ??????????????? ?????? ?????? ???
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // ????????? ??????
            }
        });

        adapter = new CustomAdapter(arrayList, this);
        adapter.setOnItemClickListener(
                new CustomAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        frame.removeAllViews();
                        ArrayList<String> pdinfo = new ArrayList<>();
                        Bundle info = new Bundle();//???????????? ?????? ?????? info ??????
                        pdinfo.add(arrayList.get(pos).getPd_name());             // 0??? ??????
                        pdinfo.add(arrayList.get(pos).getPd_brandname());        // 1??? ???????????????
                        pdinfo.add(arrayList.get(pos).getPd_profile());          // 2??? ?????????
                        pdinfo.add(arrayList.get(pos).getPrimary_fnclty());      // 3??? ???????????????
                        pdinfo.add(arrayList.get(pos).getNtk_mthd());            // 4??? ????????????
                        pdinfo.add(arrayList.get(pos).getIndiv_rawmtrl_nm());    // 5??? ???????????????
                        pdinfo.add(arrayList.get(pos).getEtc_rawmtrl_nm());      // 6??? ????????????
                        pdinfo.add(arrayList.get(pos).getCap_rawmtrl_nm());      // 7??? ?????? ??????
                        pdinfo.add(arrayList.get(pos).getIftkn_atnt_matr_cn());  // 8??? ????????????
                        pdinfo.add(arrayList.get(pos).getPrdt_shap_cd_nm());     // 9??? ????????????
                        pdinfo.add(arrayList.get(pos).getPd_code());             // 10??? ????????????
                        pdinfo.add(arrayList.get(pos).getPd_protein());          //11??? ?????????
                        pdinfo.add(arrayList.get(pos).getPd_carbohydrate());     //12??? ????????????
                        pdinfo.add(arrayList.get(pos).getPd_province());         //13??? ??????
                        pdinfo.add(arrayList.get(pos).getPd_salt());             //14??? ?????????


                        info.putStringArrayList("product", pdinfo);
                        FragmentProduct fragmentProduct = new FragmentProduct();
                        fragmentProduct.setArguments(info);
                        replaceFragment(fragmentProduct);
                    }
                }
        );
        recyclerView.setAdapter(adapter); //????????????????????? ????????? ??????


    }

    // ??? ????????? ?????????
    public void onghealthClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.g-health.kr/portal/bbs/selectBoardList.do?bbsId=U00190&menuNo=200462"));
        startActivity(intent);
    }

    public void onhanClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hani.co.kr/arti/society/health/home01.html"));
        startActivity(intent);
    }

    //??????????????? ?????? ?????????
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

        //???????????????
        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
        }

        //??????????????????
        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {
        }
    };


}

