package com.example.graduation.Object;

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
    private Float pd_avg;  //제품평균
    private Float us_avg;  //유저예상평균점수
    private String recommendation_count; //추천 횟수
    private String history_date; //추천받은날짜



    //식약처 데이터
    private String ntk_mthd;
    private String primary_fnclty; //주된기능성
    private String iftkn_atnt_matr_cn; //섭취시주의사항
    private String prdt_shap_cd_nm; //제품형태
    private String etc_rawmtrl_nm; //기타 원재료
    private String indiv_rawmtrl_nm; //기능성 원재료
    private String cap_rawmtrl_nm; //캡슐 원재료
    private String child_crtfc_yn; // 어린이기호식품품질인증여부


    //함럄정보
    private String pd_protein;  //단백질
    private String pd_carbohydrate;//탄수화물
    private String pd_province; //지방
    private String pd_salt; //나트륨

    public String getHistory_date() {
        return history_date;
    }

    public void setHistory_date(String history_date) {
        this.history_date = history_date;
    }

    public String getPd_protein() {
        return pd_protein;
    }

    public void setPd_protein(String pd_protein) {
        this.pd_protein = pd_protein;
    }

    public String getPd_province() {
        return pd_province;
    }

    public void setPd_province(String pd_province) {
        this.pd_province = pd_province;
    }

    public String getPd_carbohydrate() {
        return pd_carbohydrate;
    }

    public void setPd_carbohydrate(String pd_carbohydrate) {
        this.pd_carbohydrate = pd_carbohydrate;
    }

    public String getRecommendation_count() {
        return recommendation_count;
    }

    public void setRecommendation_count(String recommendation_count) {
        this.recommendation_count = recommendation_count;
    }


    public String getPd_salt() {
        return pd_salt;
    }

    public void setPd_salt(String pd_salt) {
        this.pd_salt = pd_salt;
    }

    public Product(){}

    public Float getPd_avg() {
        return pd_avg;
    }

    public void setPd_avg(Float pd_avg) {
        this.pd_avg = pd_avg;
    }

    public Float getUs_avg() {
        return us_avg;
    }

    public void setUs_avg(Float us_avg) {
        this.us_avg = us_avg;
    }

    public String getNtk_mthd() {
        return ntk_mthd;
    }

    public void setNtk_mthd(String ntk_mthd) {
        this.ntk_mthd = ntk_mthd;
    }

    public String getPrimary_fnclty() {
        return primary_fnclty;
    }

    public void setPrimary_fnclty(String primary_fnclty) {
        this.primary_fnclty = primary_fnclty;
    }

    public String getPd_profile() {
        return pd_profile;
    }

    public String getIftkn_atnt_matr_cn() {
        return iftkn_atnt_matr_cn;
    }

    public void setIftkn_atnt_matr_cn(String iftkn_atnt_matr_cn) {
        this.iftkn_atnt_matr_cn = iftkn_atnt_matr_cn;
    }

    public String getPrdt_shap_cd_nm() {
        return prdt_shap_cd_nm;
    }

    public void setPrdt_shap_cd_nm(String PRDT_SHAP_CD_NM) {
        this.prdt_shap_cd_nm = PRDT_SHAP_CD_NM;
    }

    public String getEtc_rawmtrl_nm() {
        return etc_rawmtrl_nm;
    }

    public void setEtc_rawmtrl_nm(String etc_rawmtrl_nm) {
        this.etc_rawmtrl_nm = etc_rawmtrl_nm;
    }

    public String getIndiv_rawmtrl_nm() {
        return indiv_rawmtrl_nm;
    }

    public void setIndiv_rawmtrl_nm(String indiv_rawmtrl_nm) {
        this.indiv_rawmtrl_nm = indiv_rawmtrl_nm;
    }

    public String getCap_rawmtrl_nm() {
        return cap_rawmtrl_nm;
    }

    public void setCap_rawmtrl_nm(String cap_rawmtrl_nm) {
        this.cap_rawmtrl_nm = cap_rawmtrl_nm;
    }

    public String getChild_crtfc_yn() {
        return child_crtfc_yn;
    }

    public void setChild_crtfc_yn(String child_crtfc_yn) {
        this.child_crtfc_yn = child_crtfc_yn;
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

}
