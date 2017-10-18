package cn.com.mangopi.android.ui.viewlistener;

import java.util.Map;

public interface LoginListener<T> extends BaseViewListener {

    void onSuccess(T data);

    void startSetNickName();

    void startMain();

    void startRegist(String openId, String unionId);

    Map<String, Object> getLoginParams();
}
