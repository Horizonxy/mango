package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.OrderBean;

import java.util.List;

public interface OrderListListener extends BaseViewListener {

    void onOrderListSuccess(List<OrderBean> orderList);
    int getPageNo();
    int getRelation();
    void onCancelSuccess(OrderBean order);
}
