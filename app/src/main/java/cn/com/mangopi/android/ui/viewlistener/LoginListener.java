package cn.com.mangopi.android.ui.viewlistener;

public interface LoginListener<T> extends BaseViewListener {

    void onSuccess(T data);

    void startSetNickName();

    void startMain();

    void startRegist();
}
