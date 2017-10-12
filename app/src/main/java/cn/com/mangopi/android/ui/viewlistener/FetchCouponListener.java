package cn.com.mangopi.android.ui.viewlistener;

public interface FetchCouponListener extends BaseViewListener {

    void onFetchCouponSuccess(long id);
    void onFetchCouponFailure(String message);
}
