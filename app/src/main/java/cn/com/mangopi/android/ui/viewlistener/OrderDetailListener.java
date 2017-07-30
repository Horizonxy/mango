package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.OrderDetailBean;

public interface OrderDetailListener extends BaseViewListener {

    void onSuccess(OrderDetailBean orderDetail);
    long getId();
}
