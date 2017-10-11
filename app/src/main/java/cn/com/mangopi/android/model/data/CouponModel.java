package cn.com.mangopi.android.model.data;

import java.util.List;
import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.CouponBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

public class CouponModel {

    public Subscription memberCouponList(int pageNo, int state, Action1 onError, Subscriber<RestResult<List<CouponBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainError(ApiManager.memberCouponList(pageNo, state), onError).subscribe(subscriber);
    }
}
