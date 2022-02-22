    package com.example.graduation;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;

    public class RegisterActivity extends AppCompatActivity {

        private FirebaseAuth mAuth;     //변수 선언(파이어베이스 인증처리)
        private DatabaseReference mRef; //실시간 데이터베이스
        private EditText mEtid, mEtpwd; //입력필드
        private Button mBtnregister;    //회원가입 버튼
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            mAuth = FirebaseAuth.getInstance();                     // ( 변수 초기화 및 초기설정
            mRef = FirebaseDatabase.getInstance().getReference();

            mEtid = findViewById(R.id.etid);
            mEtpwd = findViewById(R.id.etpwd);
            mBtnregister = findViewById(R.id.btn_register);         // 변수 초기화 및 초기설정 )

            mBtnregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //회원가입 버튼 처리 함수

                    String strid = mEtid.getText().toString();  //문자열 변수선언 및 입력받은 문자 저장
                    String strpwd = mEtpwd.getText().toString();

                    //Firebase Auth(파이어베이스를 이용한 회원가입처리)
                    mAuth.createUserWithEmailAndPassword(strid,strpwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                       //회원가입 완료시
                            if (task.isSuccessful()) {

                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                UserAccount account = new UserAccount();        //유저어카운트객체 생성

                                account.setId(firebaseUser.getEmail());         //생성된 객체에 정보 받기
                                account.setPassward(strpwd);
                                account.setUserToken(firebaseUser.getUid());
                            }
                        }
                    });
                }
            });


        }
    }