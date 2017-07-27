package cn.com.mangopi.android.ui.viewlistener;

import android.content.Context;

public interface BaseViewListener {
    void onFailure(String message);

    Context currentContext();
}
