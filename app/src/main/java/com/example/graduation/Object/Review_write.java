package com.example.graduation.Object;

public class Review_write {
    private String review; //리뷰 내용
    private String pd_code; //제품 번호

    public String getPd_code() {
        return pd_code;
    }

    public void setPd_code(String pd_code) {
        this.pd_code = pd_code;
    }

    public Review_write(String review){} //생성자 메서드
    public Review_write(){}

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Review_write(String name, String pd_code){
        this.review = review;
    }
}
