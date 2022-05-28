package com.example.graduation.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.graduation.Object.Request;
import com.example.graduation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class fragment_inquiry extends Fragment {
    private String title;
    private String contents;
    private Context ct;
    private EditText et_title;
    private EditText et_contents;

    public fragment_inquiry(){}

    public static fragment_inquiry newInstance() {
       fragment_inquiry fragment = new fragment_inquiry();
       return fragment;
    }

    private DatabaseReference mRef; //실시간 데이터베이스

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inquiry, container, false);

        mRef = FirebaseDatabase.getInstance().getReference("inquiry");

        et_title = view.findViewById(R.id.et_inquiry_title);
        et_contents = view.findViewById(R.id.et_inquiry_contents);


        ct = container.getContext();

        Button btn_inquiry = view.findViewById(R.id.btn_inquiry);
        btn_inquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ct);
                dlg.setTitle("문의 완료");
                dlg.setMessage("문의 해주셔서 감사합니다.");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dlg.show();
                Request request = new Request();
                title = et_title.getText().toString();
                contents = et_contents.getText().toString();
                request.setTitle(title);
                request.setContents(contents);

                TimeZone tz;                                        // 객체 생성
                tz = TimeZone.getTimeZone("Asia/Seoul");  // TimeZone에 표준시 설정
                Date date = new Date();
                SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.KOREAN);
                now.setTimeZone(tz);
                String getTime = now.format(date);
                
                mRef.child(getTime).setValue(request);
            }
        });
       return  view;
    }


}
