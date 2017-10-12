package cn.com.mangopi.android.model.data;

import java.util.List;
import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.CouponBean;
import cn.com.mangopi.android.model.bean.CourseCouponBean;
import cn.com.mangopi.android.model.bean.MemberWalletBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

public class CouponModel {

    public Subscription memberCouponList(int pageNo, int state, Action1 onError, Subscriber<RestResult<List<CouponBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainError(ApiManager.memberCouponList(pageNo, state), onError).subscribe(subscriber);
    }

    public Subscription courseCouponList(long couseId, Action1 onError, Subscriber<RestResult<List<CourseCouponBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainError(ApiManager.promotion(couseId), onError).subscribe(subscriber);
    }

    public Subscription fetchCoupon(long couponId, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.fetchCoupon(couponId), onSubscribe).subscribe(subscriber);
    }
}
