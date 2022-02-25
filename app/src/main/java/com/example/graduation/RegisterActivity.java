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
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;

    public class RegisterActivity extends AppCompatActivity {

        private FirebaseAuth mAuth;     //변수 선언(파이어베이스 인증처리)
        private DatabaseReference mRef; //실시간 데이터베이스
        private EditText mEtName, mEtPhone, mEtEmail, mEtPwd; //회원가입 입력필드
        private Button mBtnRegister;    //회원가입 버튼

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            mAuth = FirebaseAuth.getInstance();                     // ( 변수 초기화 및 초기설정
            mRef = FirebaseDatabase.getInstance().getReference("graduation");

            mEtName = findViewById(R.id.et_name);
            mEtPhone = findViewById(R.id.et_phone);
            mEtEmail = findViewById(R.id.et_email);
            mEtPwd = findViewById(R.id.et_pwd);
            mBtnRegister = findViewById(R.id.btn_register);         // 변수 초기화 및 초기설정 )

            mBtnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //회원가입 버튼 처리 함수

                    String strName = mEtName.getText().toString();
                    String strPhone = mEtPhone.getText().toString();
                    String strEmail = mEtEmail.getText().toString();  //문자열 변수선언 및 입력받은 문자 저장
                    String strPwd = mEtPwd.getText().toString();

                    //Firebase Auth(파이어베이스를 이용한 회원가입처   리)
                    mAuth.createUserWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                       //회원가입 완료시
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();

                                UserAccount account = new UserAccount();        //유저어카운트객체 생성
                                account.setName(strName);
                                account.setPhone(strPhone);
                                account.setUserToken(firebaseUser.getUid());    //생성된 객체에 정보 받기
                                account.setEmailId(firebaseUser.getEmail());
                                account.setPassword(strPwd);

                                //setValue : database에 insert(삽입) 행위
                                mRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                                Toast.makeText(RegisterActivity.this, "회원가입에 성공했습니다",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                                startActivity(intent);
                                finish(); //회원가입 완료 후 현재 액티비티 파괴
                            } else{
                                Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });


        }
    }