package com.example.graduation;

import java.util.Comparator;

public class Review implements Comparable<Review>, Comparator<Review>{
    private String score;
    private String pd_code;
    private String userid;
    private String review;
    private String pd_name;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public Review() {
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

    public String getPd_name() {
        return pd_name;
    }

    public void setPd_name(String pd_name) {
        this.pd_name = pd_name;
    }




    @Override
    public int compareTo(Review review) {
        if (Double.parseDouble(review.score) <Double.parseDouble(score)) {
            return 1;
        } else if (Double.parseDouble(review.score) > Double.parseDouble(score)) {
            return -1;
        }
        return 0;
    }

    @Override
    public int compare(Review review, Review t1) {
        return t1.date.compareTo( review.date);
    }
}

