package com.mango.ui.viewlistener;

import java.util.List;

public interface AddTrendListener extends BaseViewListener {

    void onSuccess();
    String getContent();
    List<String> getPics();
}
