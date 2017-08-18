package cn.com.mangopi.android.ui.viewlistener;

import java.util.Map;

public interface ProfileSettingListener extends BaseViewListener {

    void onSuccess();
    Map<String, Object> getMap();
}
