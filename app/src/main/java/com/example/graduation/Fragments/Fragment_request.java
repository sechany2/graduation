package com.example.graduation.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.graduation.Activity.MainActivity;
import com.example.graduation.Object.Request;
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

public class Fragment_request extends Fragment {
    private String title;
    private String contents;
    private Context ct;
    private EditText et_title;
    private EditText et_contents;
    private FirebaseAuth mAuth;
    private String name;
    private DatabaseReference mRef; //실시간 데이터베이스
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    public Fragment_request(){}

    public static Fragment_request newInstance() {
        Fragment_request fragment = new Fragment_request();
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        mRef = FirebaseDatabase.getInstance().getReference("Request");
        mAuth = FirebaseAuth.getInstance();

        et_title = view.findViewById(R.id.et_request_title);
        et_contents = view.findViewById(R.id.et_request_contents);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        ct = container.getContext();

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
        Button btn_request = view.findViewById(R.id.btn_request);
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(ct);
                dlg.setTitle("요청 완료");
                dlg.setMessage("요청 해주셔서 감사합니다.\n검토후 추가하겠습니다.");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

                dlg.show();
                title = et_title.getText().toString();
                contents = et_contents.getText().toString();
                Request request = new Request();
                request.setTitle(title);
                request.setContents(contents);
                request.setName(name);
                TimeZone tz;                                        // 객체 생성
                tz = TimeZone.getTimeZone("Asia/Seoul");  // TimeZone에 표준시 설정
                Date date = new Date();
                SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.KOREAN);
                now.setTimeZone(tz);
                String getTime = now.format(date);
                request.setDate(getTime);
                mRef.child(getTime).setValue(request);
            }
        });

        return  view;
    }


}