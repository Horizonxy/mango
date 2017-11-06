package cn.com.mangopi.android.model.data;

import java.util.Map;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.AppSignBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;

public class AppVisionModel {

    public Subscription appSign(Map<String, Object> map, Subscriber<RestResult<AppSignBean>> subscriber){
        return RxJavaUtils.schedulersIoMain(ApiManager.appSign(map)).subscribe(subscriber);
    }
}
