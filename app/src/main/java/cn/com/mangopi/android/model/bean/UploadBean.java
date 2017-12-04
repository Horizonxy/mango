package cn.com.mangopi.android.model.bean;

import java.io.Serializable;

public class UploadBean implements Serializable {

    private static final long serialVersionUID = 1915161730504167907L;
    private String name;
    private String type;
    private String url;
    private String file_name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
}
