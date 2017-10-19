package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.PayResultBean;

public interface OrderPayListener extends BaseViewListener {

    long getId();
    String getChannel();
    void onSuccess(PayResultBean payData);
}
