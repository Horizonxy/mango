package cn.com.mangopi.android.model.bean;

import java.io.Serializable;

public class FavBean implements Serializable {

    private static final long serialVersionUID = -7272359444213443388L;
    private long id;
    private long entity_id;
    private long entity_type_id;
    private String entity_name;
    private String logo_rsurl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(long entity_id) {
        this.entity_id = entity_id;
    }

    public long getEntity_type_id() {
        return entity_type_id;
    }

    public void setEntity_type_id(long entity_type_id) {
        this.entity_type_id = entity_type_id;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public String getLogo_rsurl() {
        return logo_rsurl;
    }

    public void setLogo_rsurl(String logo_rsurl) {
        this.logo_rsurl = logo_rsurl;
    }
}
