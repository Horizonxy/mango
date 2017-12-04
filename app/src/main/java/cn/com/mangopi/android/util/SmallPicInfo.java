package cn.com.mangopi.android.util;

import java.io.Serializable;

public class SmallPicInfo implements Serializable {
    private static final long serialVersionUID = 1967279585841273061L;
    public String url;
    public int left;
    public int top;
    public int width;
    public int height;
    public int position;

    public SmallPicInfo(String url, int left, int top, int width, int height, int position/*, Bitmap bmp*/) {
        this.url = url;
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.position = position;
    }

}