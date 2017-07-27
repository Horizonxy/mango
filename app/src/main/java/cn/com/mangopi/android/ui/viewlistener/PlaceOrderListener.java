package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.OrderBean;

import java.util.Map;

public interface PlaceOrderListener extends BaseViewListener {

    long getId();
    void onSuccess(OrderBean order);
    Map<String, Object> addOrderQuery();
}
