package com.mango.ui.viewlistener;

public interface AddBlankCardListener extends BaseViewListener {
    void onSuccess();
    String getBlankName();
    String getCardNo();
}
