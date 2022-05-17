package com.example.graduation;

public class Review {
    private String score;
    private String pd_code;
    private String userid;
    private String review;

    public Review(){}

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
