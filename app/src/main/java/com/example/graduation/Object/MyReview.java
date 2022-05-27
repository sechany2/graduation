package com.example.graduation.Object;

public class MyReview {
    private String score;
    private String pd_code;
    private String review;
    private String userid;
    private String pd_name;

    public MyReview(){}

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPd_code() {
        return pd_code;
    }

    public void setPd_code(String pd_code) {
        this.pd_code = pd_code;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPd_name() {
        return pd_name;
    }

    public void setPd_name(String pd_name) {
        this.pd_name = pd_name;
    }
}
