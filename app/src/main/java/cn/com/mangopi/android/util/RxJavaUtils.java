package cn.com.mangopi.android.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author 蒋先明
 * @date 2016/9/26
 */

public class RxJavaUtils {

    public static Observable schedulersIoMain(Observable observable){
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable schedulersIoMainNext(Observable observable, Action1 next){
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(next);
    }

    public static Observable schedulersIoMainError(Observable observable, Action1 error){
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnError(error);
    }

    public static Observable schedulersIoMainNextError(Observable observable, Action1 next, Action1 error){
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(next).doOnError(error);
    }

    public static Observable schedulersIoMainOnSubscribe(Observable observable, Action0 onSubscribe){
        return  observable.subscribeOn(Schedulers.io()).doOnSubscribe(onSubscribe).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable schedulersIoMainOnSubscribeError(Observable observable, Action0 onSubscribe, Action1 error){
        return  observable.subscribeOn(Schedulers.io()).doOnSubscribe(onSubscribe).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).doOnError(error);
    }
}
