package com.mango.model.bean;

import java.io.Serializable;
import java.util.List;

public class CourseClassifyBean implements Serializable {

    private long id;
    private String logo_rsurl;
    private String classify_name;
    private Long parent_id;
    private String remark;
    private Integer show_top;
    private Integer state;
    private List<CourseClassifyBean> details;

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

    public String getClassify_name() {
        return classify_name;
    }

    public void setClassify_name(String classify_name) {
        this.classify_name = classify_name;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getShow_top() {
        return show_top;
    }

    public void setShow_top(Integer show_top) {
        this.show_top = show_top;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<CourseClassifyBean> getDetails() {
        return details;
    }

    public void setDetails(List<CourseClassifyBean> details) {
        this.details = details;
    }
}
