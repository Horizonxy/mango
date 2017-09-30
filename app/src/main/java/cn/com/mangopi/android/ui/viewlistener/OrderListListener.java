package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.OrderBean;

import java.util.List;
import java.util.Map;

public interface OrderListListener extends BaseViewListener {

    void onOrderListSuccess(List<OrderBean> orderList);
    Map<String, Object> getQueryMap();
    void onCancelSuccess(OrderBean order);
    void onCancelScheduleSuccess(OrderBean order);
}
