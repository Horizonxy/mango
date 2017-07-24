package com.mango.model.bean;

import java.io.Serializable;
import java.util.List;

public class TutorBean implements Serializable {

    private long id;
    private String avatar_rsurl;
    private String logo_rsurl;
    private String name;
    private String tutor_jobs;
    private String city;
    private int want_count;
    private int wanted_count;
    private boolean is_favor;
    private List<CourseBean> courses;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAvatar_rsurl() {
        return avatar_rsurl;
    }

    public void setAvatar_rsurl(String avatar_rsurl) {
        this.avatar_rsurl = avatar_rsurl;
    }

    public String getLogo_rsurl() {
        return logo_rsurl;
    }

    public void setLogo_rsurl(String logo_rsurl) {
        this.logo_rsurl = logo_rsurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTutor_jobs() {
        return tutor_jobs;
    }

    public void setTutor_jobs(String tutor_jobs) {
        this.tutor_jobs = tutor_jobs;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getWant_count() {
        return want_count;
    }

    public void setWant_count(int want_count) {
        this.want_count = want_count;
    }

    public int getWanted_count() {
        return wanted_count;
    }

    public void setWanted_count(int wanted_count) {
        this.wanted_count = wanted_count;
    }

    public boolean is_favor() {
        return is_favor;
    }

    public void setIs_favor(boolean is_favor) {
        this.is_favor = is_favor;
    }

    public List<CourseBean> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseBean> courses) {
        this.courses = courses;
    }
}
