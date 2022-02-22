package com.example.graduation;

//사용자 정보 모델 클래스
public class UserAccount {
    private String id;
    private String passward;
    private String userToken; //firebase Uid
   public UserAccount(){} // 기본 생성자

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }
}
