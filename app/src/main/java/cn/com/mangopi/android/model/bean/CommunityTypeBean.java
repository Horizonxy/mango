package cn.com.mangopi.android.model.bean;

import java.io.Serializable;

public class CommunityTypeBean implements Serializable {

    private long id;
    private String type_name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}
