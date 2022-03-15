package com.example.graduation;

public class Product {
    private String pd_profile; //제품 이미지
    private String pd_code; // 제품 코드
    private String pd_name; //제품 이름
    private String pd_price; //제품 가격
    private String pd_totalcapacity; // 제품 총용량
    private String pd_servingsize;  // 1회 섭취용량
    private String pd_oncecalorise;  // 1회 섭취칼로리
    private String pd_brandname;   //브랜드이름
    private String form_code;   // 형태 코드
    private String cd_code;   // 종류 코드
    private String pd_classification; //분류코드


    public Product(){}

    public String getPd_profile() {
        return pd_profile;
    }

    public void setPd_profile(String pd_profile) {
        this.pd_profile = pd_profile;
    }

    public String getPd_code() {
        return pd_code;
    }

    public void setPd_code(String pd_code) {
        this.pd_code = pd_code;
    }

    public String getPd_name() {
        return pd_name;
    }

    public void setPd_name(String pd_name) {
        this.pd_name = pd_name;
    }

    public String getPd_price() {
        return pd_price;
    }

    public void setPd_price(String pd_price) {
        this.pd_price = pd_price;
    }

    public String getPd_totalcapacity() {
        return pd_totalcapacity;
    }

    public void setPd_totalcapacity(String pd_totalcapacity) {
        this.pd_totalcapacity = pd_totalcapacity;
    }

    public String getPd_servingsize() {
        return pd_servingsize;
    }

    public void setPd_servingsize(String pd_servingsize) {
        this.pd_servingsize = pd_servingsize;
    }

    public String getPd_oncecalorise() {
        return pd_oncecalorise;
    }

    public void setPd_oncecalorise(String pd_oncecalorise) {
        this.pd_oncecalorise = pd_oncecalorise;
    }

    public String getPd_brandname() {
        return pd_brandname;
    }

    public void setPd_brandname(String pd_brandname) {
        this.pd_brandname = pd_brandname;
    }

    public String getForm_code() {
        return form_code;
    }

    public void setForm_code(String form_code) {
        this.form_code = form_code;
    }

    public String getCd_code() {
        return cd_code;
    }

    public void setCd_code(String cd_code) {
        this.cd_code = cd_code;
    }

    public String getPd_classification() {
        return pd_classification;
    }

    public void setPd_classification(String pd_classification) {
        this.pd_classification = pd_classification;
    }
}
