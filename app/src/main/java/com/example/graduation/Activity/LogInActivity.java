package com.example.graduation.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.graduation.R;
import com.example.graduation.Object.UserAccount;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LogInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;     //변수 선언(파이어베이스 인증처리)
    private DatabaseReference mRef; //실시간 데이터베이스
    private EditText mEtEmail, mEtPwd; //로그인 입력필드
    private  FirebaseUser currentUser;
    private static final int REQ_SIGN_GOOGLE=100;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton btn_google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        //툴바
        Toolbar toolbar =findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("graduation");


        mAuth = FirebaseAuth.getInstance();                     // ( 변수 초기화 및 초기설정

        mRef = FirebaseDatabase.getInstance().getReference("graduation");
        AuthUI.getInstance()
                .signOut(this);
        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);

        Button btn_login = findViewById(R.id.btn_login);

        btn_google=findViewById(R.id.btn_google);
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent,REQ_SIGN_GOOGLE);
            }
        });



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인 요청
                String strEmail = mEtEmail.getText().toString();  //문자열 변수선언 및 입력받은 문자 저장
                String strPwd = mEtPwd.getText().toString();
                if(strEmail.equals("admin")){
                    strEmail="admin@a.com";
                }
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
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser user) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //구글 인증 결과
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_SIGN_GOOGLE){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            if(task.isSuccessful()){

                GoogleSignInAccount account=task.getResult();//구글 정보 오브젝트 생성
                resultLogin(account);                                           //결과값 출력 메소드
            }else{
                Log.e(task.getException().toString(),"exception");
            }
        }
    }

    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){    //로그인 성공
                            //Log.e("확인","로그확인");
                            currentUser = mAuth.getCurrentUser();
                            UserAccount userAccount = new UserAccount();
                            userAccount.setName(account.getDisplayName());
                            userAccount.setUserToken(currentUser.getUid());    //생성된 객체에 정보 받기
                            userAccount.setEmailId(account.getEmail());
                            userAccount.setPassword("구글 로그인");
                            userAccount.setPhone("구글 로그인");
                            mRef.child("UserAccount").child(currentUser.getUid()).setValue(userAccount);
                            mRef.child("UserAccount").child(currentUser.getUid()).child("email").setValue(account.getEmail());
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        }else{
                            Log.e(task.getException().toString(),task.getResult().toString());
                        }
                    }
                });

    }
}