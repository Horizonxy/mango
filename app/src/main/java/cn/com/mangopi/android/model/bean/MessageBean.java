package cn.com.mangopi.android.model.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.Date;

public class MessageBean implements Serializable {

    private static final long serialVersionUID = -4397980617407996084L;
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

    private String showContent;
    public String getShowContent(){
        if(!TextUtils.isEmpty(showContent)){
            return  showContent;
        }
        StringBuilder content = new StringBuilder();
        if(!TextUtils.isEmpty(title)){
            content.append(title);
        }
        if(!TextUtils.isEmpty(result)){
            if(!TextUtils.isEmpty(title)){
                content.append("：").append(result);
            } else {
                content.append(result);
            }
        }
        if(!TextUtils.isEmpty(remark)){
            if(!TextUtils.isEmpty(title)){
                if(!TextUtils.isEmpty(result)){
                    content.append("，").append(remark);
                } else {
                    content.append("：").append(remark);
                }
            } else {
                if(!TextUtils.isEmpty(result)){
                    content.append("，").append(remark);
                } else {
                    content.append(remark);
                }
            }
        }
        showContent = content.toString();
        return showContent;
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
}
