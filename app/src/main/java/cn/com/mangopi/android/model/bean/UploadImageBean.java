package cn.com.mangopi.android.model.bean;

import android.net.Uri;

import java.io.Serializable;

public class UploadImageBean implements Serializable {
    public static final int ADD_BTN = -1;//添加图片按钮
    public static final int READY = 0;//准备上传
    public static final int UPLOADED = 1;//已上传成功
    public static final int RETRY = 2;//重试
    public static final int UPLOADING = 3;//正在上传中

    String localPath;
    Uri localUri;
    String name;
    int type;

    public UploadImageBean() {
        super();
    }

    public UploadImageBean(int type) {
        super();
        this.type = type;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public Uri getLocalUri() {
        return localUri;
    }

    public void setLocalUri(Uri localUri) {
        this.localUri = localUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}