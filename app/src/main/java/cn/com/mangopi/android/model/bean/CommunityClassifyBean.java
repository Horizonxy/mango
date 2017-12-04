package cn.com.mangopi.android.model.bean;

import java.io.Serializable;

public class CommunityClassifyBean implements Serializable {

    private static final long serialVersionUID = -6260101707922645159L;
    private long id;
    private String classic_name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassic_name() {
        return classic_name;
    }

    public void setClassic_name(String classic_name) {
        this.classic_name = classic_name;
    }
}
