package cn.com.mangopi.android.ui.viewlistener;

import java.util.Map;

import cn.com.mangopi.android.model.bean.AppSignBean;

public interface AppVisionListener {

    Map<String, Object> getAppSignMap();
    void onAppSignSuccess(AppSignBean appSign);
}
