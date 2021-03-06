package cn.com.mangopi.android.ui.viewlistener;

import java.util.Map;

import cn.com.mangopi.android.Constants;

public interface UpdateRoleListener extends BaseViewListener {

    void onSuccess(Constants.UserIndentity indentity);
    Map<String, Object> getUpgradeMap();
}
