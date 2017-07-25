package com.mango.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class AdvertBean implements Serializable {

    private long id;
    private String title;
    private String type;
    private String intro;
    private String logo_rsurl;
    private Integer state;
    private String state_label;
    private Date publish_time;
    private String position;
    private List<DetailsBean> details;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogo_rsurl() {
        return logo_rsurl;
    }

    public void setLogo_rsurl(String logo_rsurl) {
        this.logo_rsurl = logo_rsurl;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Date publish_time) {
        this.publish_time = publish_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean implements Serializable {

        private long id;
        private String file_path;
        private Integer bind_type;
        private Integer entity_type_id;
        private Long entity_id;
        private String entity_name;
        private String click_url;
        private String description;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        public Integer getBind_type() {
            return bind_type;
        }

        public void setBind_type(Integer bind_type) {
            this.bind_type = bind_type;
        }

        public Integer getEntity_type_id() {
            return entity_type_id;
        }

        public void setEntity_type_id(Integer entity_type_id) {
            this.entity_type_id = entity_type_id;
        }

        public Long getEntity_id() {
            return entity_id;
        }

        public void setEntity_id(Long entity_id) {
            this.entity_id = entity_id;
        }

        public String getEntity_name() {
            return entity_name;
        }

        public void setEntity_name(String entity_name) {
            this.entity_name = entity_name;
        }

        public String getClick_url() {
            return click_url;
        }

        public void setClick_url(String click_url) {
            this.click_url = click_url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
