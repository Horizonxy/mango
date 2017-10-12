package cn.com.mangopi.android.presenter;


import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.mangopi.android.model.bean.AdvertBean;
import cn.com.mangopi.android.model.bean.BulletinBean;
import cn.com.mangopi.android.model.bean.CommonBean;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.AdvertModel;
import cn.com.mangopi.android.model.data.BulletinModel;
import cn.com.mangopi.android.model.data.CourseModel;
import cn.com.mangopi.android.model.db.CommonDaoImpl;
import cn.com.mangopi.android.ui.viewlistener.HomeFragmentListener;
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
        boolean show = true;
        if(list != null && list.size() > 0){
            List<AdvertBean> data = (List<AdvertBean>) list.get(0).getData();
            List<AdvertBean> coupons = new ArrayList<>();
            for (int i = 0; data != null && i < data.size(); i++){
                if("4".equals(data.get(i).getType())){
                    coupons.add(data.get(i));
                }
            }
            if(coupons.size() > 0){
                data.removeAll(coupons);
            }
            homeListener.onSuccess(position, data);
            show = false;
        }

        Context context = homeListener.currentContext();
        Subscription subscription = advertModel.homeAdvert(homeListener.getUserIdentity(), position,
                new CreateLoading(context, show), new BaseLoadingSubscriber<RestResult<ArrayList<AdvertBean>>>() {

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
        boolean show = true;
        if(list != null && list.size() > 0){
            List<BulletinBean> data = (List<BulletinBean>) list.get(0).getData();
            homeListener.onSuccess(data);
            show = false;
        }

        Context context = homeListener.currentContext();
        Subscription subscription = bulletinModel.homeBulletinList(1, 10, new CreateLoading(context, show), new BaseLoadingSubscriber<RestResult<ArrayList<BulletinBean>>>() {
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
        boolean show = true;
        if(list != null && list.size() > 0){
            List<CourseClassifyBean> data = (List<CourseClassifyBean>) list.get(0).getData();
            homeListener.onClassifySuccess(data);
            show = false;
        }

        Context context = homeListener.currentContext();
        Map<String, Long> map = new HashMap<String, Long>();
        map.put("show_top", 1L);
        Subscription subscription = courseModel.getClassify(map, new CreateLoading(context, show), new BaseLoadingSubscriber<RestResult<ArrayList<CourseClassifyBean>>>() {

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
