package com.mango.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.mango.Application;
import com.mango.R;
import com.mango.util.AppUtils;
import com.mango.util.NetUtils;
import com.mango.util.SimpleSubscriber;

import rx.Observable;
import rx.functions.Func1;

public class NetReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        Observable.just(intent)
                .filter(new Func1<Intent, Boolean>() {
                    @Override
                    public Boolean call(Intent intent) {
                        return intent != null;
                    }
                })
                .map(new Func1<Intent, String>() {
                    @Override
                    public String call(Intent intent) {
                        return intent.getAction();
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return ConnectivityManager.CONNECTIVITY_ACTION.equals(s);
                    }
                }).subscribe(new SimpleSubscriber<String>(){
            @Override
            public void onNextRx(String obj) {
//                BusEvent.NetEvent event = new BusEvent.NetEvent();
//                event.setHasNet(NetUtils.isNetworkConnected(context));
//                Bus.getDefault().post(event);

                if(!NetUtils.isNetworkConnected(context)){
                    AppUtils.showToast(Application.application.getTopActivity(), R.string.noconnection);
                }
            }
        });
    }

}
