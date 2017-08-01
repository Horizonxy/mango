package cn.com.mangopi.android.ui.viewlistener;

public interface OrderPayListener extends BaseViewListener {

    long getId();
    String getChannel();
    void onSuccess(String payData);
}
