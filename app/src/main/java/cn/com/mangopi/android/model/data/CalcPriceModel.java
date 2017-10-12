package cn.com.mangopi.android.model.data;

import java.util.Map;
import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.CalcPriceBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class CalcPriceModel {

    public Subscription calcPrice(Map<String, Object> map, Action0 onSubscriber, Subscriber<RestResult<CalcPriceBean>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.calcPrice(map), onSubscriber).subscribe(subscriber);
    }
}
