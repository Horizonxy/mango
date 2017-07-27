package cn.com.mangopi.android.model.data;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.BulletinBean;
import cn.com.mangopi.android.model.bean.RestResult;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * @author 蒋先明
 * @date 2017/7/8
 */

public class BulletinModel {

    public Subscription homeBulletinList(int pageNo, int pageSize, Action0 onSubscribe, Subscriber<RestResult<ArrayList<BulletinBean>>> subscriber){
        return ApiManager.getBulletinList(pageNo, pageSize)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(onSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
