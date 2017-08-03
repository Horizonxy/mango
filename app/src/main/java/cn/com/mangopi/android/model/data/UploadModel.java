package cn.com.mangopi.android.model.data;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.bean.UploadBean;
import cn.com.mangopi.android.util.RxJavaUtils;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class UploadModel {

    public Subscription upload(long entityId, int entityTypeId, RequestBody file, Action0 onSubscribe, Subscriber<RestResult<UploadBean>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.upload(entityId, entityTypeId, file), onSubscribe).subscribe(subscriber);
    }

    public Subscription uploadWithOutLoading(long entityId, int entityTypeId, RequestBody file, Subscriber<RestResult<UploadBean>> subscriber){
        return RxJavaUtils.schedulersIoMain(ApiManager.upload(entityId, entityTypeId, file)).subscribe(subscriber);
    }
}
