package com.mango.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class CourseBean implements Serializable {

    private long id;
    private String course_title;
    private String logo_rsurl;
    private String city;
    private String course_content;
    private String service_time;
    private String each_time;
    private Integer state;
    private String state_label;
    private Integer approve_state;
    private String approve_state_label;
    private int want_count;
    private String type_name;
    private BigDecimal sale_price;
    private String type_method;
    private String avatar_rsurl;
    private String member_name;
    private long member_id;
    private String tutor_jobs;
    private String hot_types;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getLogo_rsurl() {
        return logo_rsurl;
    }

    public void setLogo_rsurl(String logo_rsurl) {
        this.logo_rsurl = logo_rsurl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCourse_content() {
        return course_content;
    }

    public void setCourse_content(String course_content) {
        this.course_content = course_content;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getEach_time() {
        return each_time;
    }

    public void setEach_time(String each_time) {
        this.each_time = each_time;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getState_label() {
        return state_label;
    }

    public void setState_label(String state_label) {
        this.state_label = state_label;
    }

    public Integer getApprove_state() {
        return approve_state;
    }

    public void setApprove_state(Integer approve_state) {
        this.approve_state = approve_state;
    }

    public String getApprove_state_label() {
        return approve_state_label;
    }

    public void setApprove_state_label(String approve_state_label) {
        this.approve_state_label = approve_state_label;
    }

    public int getWant_count() {
        return want_count;
    }

    public void setWant_count(int want_count) {
        this.want_count = want_count;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public BigDecimal getSale_price() {
        return sale_price;
    }

    public void setSale_price(BigDecimal sale_price) {
        this.sale_price = sale_price;
    }

    public String getType_method() {
        return type_method;
    }

    public void setType_method(String type_method) {
        this.type_method = type_method;
    }

    public String getAvatar_rsurl() {
        return avatar_rsurl;
    }

    public void setAvatar_rsurl(String avatar_rsurl) {
        this.avatar_rsurl = avatar_rsurl;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public long getMember_id() {
        return member_id;
    }

    public void setMember_id(long member_id) {
        this.member_id = member_id;
    }

    public String getTutor_jobs() {
        return tutor_jobs;
    }

    public void setTutor_jobs(String tutor_jobs) {
        this.tutor_jobs = tutor_jobs;
    }

    public String getHot_types() {
        return hot_types;
    }

    public void setHot_types(String hot_types) {
        this.hot_types = hot_types;
    }
}
