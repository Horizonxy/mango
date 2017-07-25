package com.mango.presenter;


import android.content.Context;

import com.mango.Constants;
import com.mango.R;
import com.mango.model.bean.AdvertBean;
import com.mango.model.bean.BulletinBean;
import com.mango.model.bean.CommonBean;
import com.mango.model.bean.CourseClassifyBean;
import com.mango.model.bean.RestResult;
import com.mango.model.data.AdvertModel;
import com.mango.model.data.BulletinModel;
import com.mango.model.data.CourseModel;
import com.mango.model.db.CommonDaoImpl;
import com.mango.ui.viewlistener.HomeFragmentListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

public class HomePresenter extends BasePresenter {

    AdvertModel advertModel;
    HomeFragmentListener homeListener;
    BulletinModel bulletinModel;
    CourseModel courseModel;
    CommonDaoImpl commonDao;
    String bulletinKey;
    String classifyListKey;

    public HomePresenter( CourseModel courseModel, BulletinModel bulletinModel, AdvertModel advertModel, HomeFragmentListener homeListener) {
        this.courseModel = courseModel;
        this.advertModel = advertModel;
        this.bulletinModel = bulletinModel;
        this.homeListener = homeListener;
        bulletinKey = "home_bulletin_cache_key";
        classifyListKey = "home_classify_list_cache_key";
        this.commonDao = new CommonDaoImpl(homeListener.currentContext());
    }

    public void getAdvert(String position){
        String key = position + "_" + homeListener.getUserIdentity();
        List<CommonBean> list = commonDao.findByColumn(CommonBean.DATA_TYPE, key);
        if(list != null && list.size() > 0){
            List<AdvertBean> data = (List<AdvertBean>) list.get(0).getData();
            homeListener.onSuccess(position, data);
        }

        Context context = homeListener.currentContext();
        Subscription subscription = advertModel.homeAdvert(homeListener.getUserIdentity(), position, new CreateLoading(context, context.getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<ArrayList<AdvertBean>>>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null) {
                    homeListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<ArrayList<AdvertBean>> restResult) {
                if(restResult != null && restResult.isSuccess()){
                    homeListener.onSuccess(position, restResult.getData());

                    commonDao.saveData(key, restResult.getData());
                }
            }
        });
        addSubscription(subscription);
    }

    public void getHomeBulletinList(){
        List<CommonBean> list = commonDao.findByColumn(CommonBean.DATA_TYPE, bulletinKey);
        if(list != null && list.size() > 0){
            List<BulletinBean> data = (List<BulletinBean>) list.get(0).getData();
            homeListener.onSuccess(data);
        }

        Context context = homeListener.currentContext();
        Subscription subscription = bulletinModel.homeBulletinList(1, 10, new CreateLoading(context, context.getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<ArrayList<BulletinBean>>>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null) {
                    homeListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<ArrayList<BulletinBean>> restResult) {
                if(restResult != null && restResult.isSuccess()){
                    homeListener.onSuccess(restResult.getData());

                    commonDao.saveData(bulletinKey, restResult.getData());
                }
            }
        });
        addSubscription(subscription);
    }

    public void getCourseClassify(){
        List<CommonBean> list = commonDao.findByColumn(CommonBean.DATA_TYPE, classifyListKey);
        if(list != null && list.size() > 0){
            List<CourseClassifyBean> data = (List<CourseClassifyBean>) list.get(0).getData();
            homeListener.onClassifySuccess(data);
        }

        Context context = homeListener.currentContext();
        Map<String, Long> map = new HashMap<String, Long>();
        map.put("show_top", 1L);
        Subscription subscription = courseModel.getClassify(map, new CreateLoading(context, context.getString(R.string.please_wait)), new BaseLoadingSubscriber<RestResult<ArrayList<CourseClassifyBean>>>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null) {
                    homeListener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<ArrayList<CourseClassifyBean>> restResult) {
                if(restResult != null && restResult.isSuccess()){
                    homeListener.onClassifySuccess(restResult.getData());

                    commonDao.saveData(classifyListKey, restResult.getData());
                }
            }
        });
        addSubscription(subscription);
    }
}
