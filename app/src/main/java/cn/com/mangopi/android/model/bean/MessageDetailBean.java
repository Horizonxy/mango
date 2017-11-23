package cn.com.mangopi.android.model.bean;

import java.util.Date;
import java.util.List;

public class MessageDetailBean {
    private long id;
    private String send_user_type;
    private String send_user_name;
    private long send_user_id;
    private String title;
    private String result;
    private String remark;
    private Integer state;
    private String state_label;
    private String url;
    private Date create_time;
    private String create_time_label;
    private long entity_type;
    private long entity_id;
    private String entity_name;
    private List<MessageDetailBean> replys;

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public List<MessageDetailBean> getReplys() {
        return replys;
    }

    public void setReplys(List<MessageDetailBean> replys) {
        this.replys = replys;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSend_user_type() {
        return send_user_type;
    }

    public void setSend_user_type(String send_user_type) {
        this.send_user_type = send_user_type;
    }

    public String getSend_user_name() {
        return send_user_name;
    }

    public void setSend_user_name(String send_user_name) {
        this.send_user_name = send_user_name;
    }

    public long getSend_user_id() {
        return send_user_id;
    }

    public void setSend_user_id(long send_user_id) {
        this.send_user_id = send_user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getCreate_time_label() {
        return create_time_label;
    }

    public void setCreate_time_label(String create_time_label) {
        this.create_time_label = create_time_label;
    }

    public long getEntity_type() {
        return entity_type;
    }

    public void setEntity_type(long entity_type) {
        this.entity_type = entity_type;
    }

    public long getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(long entity_id) {
        this.entity_id = entity_id;
    }
}
