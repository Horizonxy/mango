package cn.com.mangopi.android.model.bean;

import java.io.Serializable;

public class SearchBean implements Serializable {

    private static final long serialVersionUID = 7975555459790625910L;
    private long id;
    private int entity_type_id;
    private String title;
    private String logo_rsurl;
    private String intro;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getEntity_type_id() {
        return entity_type_id;
    }

    public void setEntity_type_id(int entity_type_id) {
        this.entity_type_id = entity_type_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo_rsurl() {
        return logo_rsurl;
    }

    public void setLogo_rsurl(String logo_rsurl) {
        this.logo_rsurl = logo_rsurl;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
