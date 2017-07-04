package com.mango.ui.viewlistener;

import android.content.Context;

public interface BaseViewListener {
    void onFailure(String message);

    Context currentContext();
}
