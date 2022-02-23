package com.example.graduation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;     //변수 선언(파이어베이스 인증처리)
    private DatabaseReference mRef; //실시간 데이터베이스
    private EditText mEtEmail, mEtPwd; //로그인 입력필드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();                     // ( 변수 초기화 및 초기설정
        mRef = FirebaseDatabase.getInstance().getReference("graduation");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인 요청
                String strEmail = mEtEmail.getText().toString();  //문자열 변수선언 및 입력받은 문자 저장
                String strPwd = mEtPwd.getText().toString();

                mAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //로그인 성공
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish(); //로그인 완료 후 현재 액티비티 파괴
                        }else{
                            Toast.makeText(LogInActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 화면으로 이동
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}