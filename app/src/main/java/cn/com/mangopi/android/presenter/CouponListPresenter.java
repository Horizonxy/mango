package cn.com.mangopi.android.presenter;

import java.util.List;
import cn.com.mangopi.android.model.bean.CouponBean;
import cn.com.mangopi.android.model.bean.CourseCouponBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.CouponModel;
import cn.com.mangopi.android.ui.viewlistener.CouponListListener;
import rx.Subscription;
import rx.functions.Action1;

public class CouponListPresenter extends BasePresenter {

    CouponModel couponModel;
    CouponListListener couponListListener;

    public CouponListPresenter(CouponListListener couponListListener) {
        this.couponListListener = couponListListener;
        this.couponModel = new CouponModel();
    }

    public void memberCouponList(){
        Subscription subscription = couponModel.memberCouponList(couponListListener.getPageNo(), couponListListener.getState(), new Action1<Throwable>() {
            @Override
            public void call(Throwable e) {
                if (e != null) {
                    e.printStackTrace();
                }
                couponListListener.onFailure(null);
            }
        }, new BaseSubscriber<RestResult<List<CouponBean>>>(){
            @Override
            public void onNext(RestResult<List<CouponBean>> restResult) {
                super.onNext(restResult);
                if (restResult == null) {
                    couponListListener.onFailure(null);
                } else {
                    if (restResult.isSuccess()) {
                        couponListListener.onCouponListSuccess(restResult.getData());
                    } else {
                        couponListListener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    public void courseCouponList(long courseId){
        Subscription subscription = couponModel.courseCouponList(courseId, new Action1<Throwable>() {
            @Override
            public void call(Throwable e) {
                if (e != null) {
                    e.printStackTrace();
                }
                couponListListener.onFailure(null);
            }
        }, new BaseSubscriber<RestResult<List<CourseCouponBean>>>(){
            @Override
            public void onNext(RestResult<List<CourseCouponBean>> restResult) {
                super.onNext(restResult);
                if (restResult == null) {
                    couponListListener.onFailure(null);
                } else {
                    if (restResult.isSuccess()) {
                        couponListListener.onCourseCouponListSuccess(restResult.getData());
                    } else {
                        couponListListener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }
}
