package cn.com.mangopi.android.model.bean;

import java.io.Serializable;

public class AppVisionBean implements Serializable {

    private String register_id;
    private String version_name;
    private int version_code;
    private int min_version_code;
    private String download_url;
    private String app_name;
    private long app_size;
    private String content;

    public String getRegister_id() {
        return register_id;
    }

    public void setRegister_id(String register_id) {
        this.register_id = register_id;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public int getMin_version_code() {
        return min_version_code;
    }

    public void setMin_version_code(int min_version_code) {
        this.min_version_code = min_version_code;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public long getApp_size() {
        return app_size;
    }

    public void setApp_size(long app_size) {
        this.app_size = app_size;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
