package com.mango.ui.viewlistener;

import com.mango.model.bean.OrderBean;

import java.util.List;

public interface OrderListListener extends BaseViewListener {

    void onOrderListSuccess(List<OrderBean> orderList);
    int getPageNo();
    int getRelation();
}
