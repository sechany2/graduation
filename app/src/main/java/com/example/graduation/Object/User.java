package com.example.graduation.Object;

public class User {
    private String Name;        //이름
    private String password;    //비밀번호

    private String reviewVolume; //리뷰 수
    private String UserID;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getReviewVolume() {
        return reviewVolume;
    }

    public void setReviewVolume(String reviewVolume) {
        this.reviewVolume = reviewVolume;
    }
}
