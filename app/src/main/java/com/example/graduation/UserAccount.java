package com.example.graduation;

//사용자 정보 모델 클래스
public class UserAccount {
    private String Name;        //이름
    private String Phone;       //전화번호
    private String emailId;     //이메일 아이디
    private String password;    //비밀번호
    private String userToken; //firebase Uid 고유 토큰




    public UserAccount(){} // 기본 생성자

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String id) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
