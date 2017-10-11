package cn.com.mangopi.android.ui.viewlistener;

import java.util.List;

import cn.com.mangopi.android.model.bean.CouponBean;

public interface CouponListListener extends BaseViewListener {

    int getPageNo();
    int getState();
    void onCouponListSuccess(List<CouponBean> couponList);
}
