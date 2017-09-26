package cn.com.mangopi.android.ui.viewlistener;

public interface TrendForwardListener extends BaseViewListener {
    long getForwardId();
    String getContent();
    void onForwardSuccess();
}
