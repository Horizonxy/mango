package com.mango.model.bean;

import java.io.Serializable;
import java.util.Date;

public class MemberBean implements Serializable {

    public static final String DATA_TYPE = "member_data";

    private Long id;
    private String[] user_identitys;
    private String user_identity_label;
    private String avatar_rsurl;
    private String nick_name;
    private String my_signature;
    private String name;
    private Integer gender;
    private String gender_label;
    private Date birthday;
    private String college;
    private String profession;
    private String enter_school;
    private String qq;
    private String weixin;
    private String mobile;
    private String email;
    private String project_experience;
    private String work_experience;
    private String resume_rsurl;
    private String self_evaluation;
    private Long order_count;
    private Long project_count;
    private Long course_count;
    private Long fav_count;
    private Long trend_count;
    private Long message_count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getUser_identitys() {
        return user_identitys;
    }

    public void setUser_identitys(String[] user_identitys) {
        this.user_identitys = user_identitys;
    }

    public String getUser_identity_label() {
        return user_identity_label;
    }

    public void setUser_identity_label(String user_identity_label) {
        this.user_identity_label = user_identity_label;
    }

    public String getAvatar_rsurl() {
        return avatar_rsurl;
    }

    public void setAvatar_rsurl(String avatar_rsurl) {
        this.avatar_rsurl = avatar_rsurl;
    }

    public String getMy_signature() {
        return my_signature;
    }

    public void setMy_signature(String my_signature) {
        this.my_signature = my_signature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getGender_label() {
        return gender_label;
    }

    public void setGender_label(String gender_label) {
        this.gender_label = gender_label;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEnter_school() {
        return enter_school;
    }

    public void setEnter_school(String enter_school) {
        this.enter_school = enter_school;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProject_experience() {
        return project_experience;
    }

    public void setProject_experience(String project_experience) {
        this.project_experience = project_experience;
    }

    public String getWork_experience() {
        return work_experience;
    }

    public void setWork_experience(String work_experience) {
        this.work_experience = work_experience;
    }

    public String getResume_rsurl() {
        return resume_rsurl;
    }

    public void setResume_rsurl(String resume_rsurl) {
        this.resume_rsurl = resume_rsurl;
    }

    public String getSelf_evaluation() {
        return self_evaluation;
    }

    public void setSelf_evaluation(String self_evaluation) {
        this.self_evaluation = self_evaluation;
    }

    public Long getOrder_count() {
        return order_count;
    }

    public void setOrder_count(Long order_count) {
        this.order_count = order_count;
    }

    public Long getProject_count() {
        return project_count;
    }

    public void setProject_count(Long project_count) {
        this.project_count = project_count;
    }

    public Long getCourse_count() {
        return course_count;
    }

    public void setCourse_count(Long course_count) {
        this.course_count = course_count;
    }

    public Long getFav_count() {
        return fav_count;
    }

    public void setFav_count(Long fav_count) {
        this.fav_count = fav_count;
    }

    public Long getTrend_count() {
        return trend_count;
    }

    public void setTrend_count(Long trend_count) {
        this.trend_count = trend_count;
    }

    public Long getMessage_count() {
        return message_count;
    }

    public void setMessage_count(Long message_count) {
        this.message_count = message_count;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
