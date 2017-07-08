package com.mango.presenter;


import android.content.Context;

import com.mango.R;
import com.mango.model.bean.AdvertBean;
import com.mango.model.bean.BulletinBean;
import com.mango.model.bean.CourseClassifyBean;
import com.mango.model.bean.RestResult;
import com.mango.model.data.AdvertModel;
import com.mango.model.data.BulletinModel;
import com.mango.model.data.CourseModel;
import com.mango.ui.viewlistener.HomeFragmentListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

public class HomePresenter extends BasePresenter {

    AdvertModel advertModel;
    HomeFragmentListener homeListener;
    BulletinModel bulletinModel;
    CourseModel courseModel;

    public HomePresenter( CourseModel courseModel, BulletinModel bulletinModel, AdvertModel advertModel, HomeFragmentListener homeListener) {
        this.courseModel = courseModel;
        this.advertModel = advertModel;
        this.bulletinModel = bulletinModel;
        this.homeListener = homeListener;
    }

    public void getAdvert(String position){
        Context context = homeListener.currentContext();
        Subscription subscription = advertModel.homeAdvert(homeListener.getUserIdentity(), position, new CreateLoading(context, context.getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<List<AdvertBean>>>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null) {
                    homeListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<List<AdvertBean>> restResult) {
                if(restResult != null && restResult.isSuccess()){
                    homeListener.onSuccess(position, restResult.getData());
                }
            }
        });
        addSubscription(subscription);
    }

    public void getHomeBulletinList(){
        Context context = homeListener.currentContext();
        Subscription subscription = bulletinModel.homeBulletinList(1, 10, new CreateLoading(context, context.getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<List<BulletinBean>>>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null) {
                    homeListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<List<BulletinBean>> restResult) {
                if(restResult != null && restResult.isSuccess()){
                    homeListener.onSuccess(restResult.getData());
                }
            }
        });
        addSubscription(subscription);
    }

    public void getCourseClassify(){
        Context context = homeListener.currentContext();
        Map<String, Long> map = new HashMap<String, Long>();
        map.put("show_top", 1L);
        Subscription subscription = courseModel.getClassify(map, new CreateLoading(context, context.getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<List<CourseClassifyBean>>>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null) {
                    homeListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<List<CourseClassifyBean>> restResult) {
                if(restResult != null && restResult.isSuccess()){
                    homeListener.onClassifySuccess(restResult.getData());
                }
            }
        });
        addSubscription(subscription);
    }
}
