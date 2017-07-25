package com.mango.ui.viewlistener;

import com.mango.model.bean.OrderBean;

import java.util.Map;

public interface PlaceOrderListener extends BaseViewListener {

    long getId();
    void onSuccess(OrderBean order);
    Map<String, Object> addOrderQuery();
}
