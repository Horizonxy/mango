package com.mango.model.bean;

import java.io.Serializable;

public class MemberBean implements Serializable {

    private Long id;
    private String[] user_identitys;
    private String user_identity_label;
    private String avatar_rsurl;

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
}
