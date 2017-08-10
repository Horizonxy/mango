package cn.com.mangopi.android.model.data;

import java.util.List;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.MessageBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

public class MessageModel {

    public Subscription getMessageList(int pageNo, Action1 onError, Subscriber<RestResult<List<MessageBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainError(ApiManager.getMessageList(pageNo), onError).subscribe(subscriber);
    }

    public Subscription getMessageCheck(Subscriber<RestResult<String>> subscriber){
        return RxJavaUtils.schedulersIoMain(ApiManager.getMessageCheck()).subscribe(subscriber);
    }

    public Subscription readMessage(long id, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMain(ApiManager.readMessage(id)).subscribe(subscriber);
    }
}
