package com.mango.presenter;

import android.content.Context;
import android.content.DialogInterface;

import com.mango.Application;
import com.mango.R;
import com.mango.ui.widget.LoadingDialog;
import com.mango.util.AppUtils;
import com.mango.util.NetUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter {

    CompositeSubscription mCompositeSubscription;
    LoadingDialog loadingDialog;
    List<Context> contexts;
    Context context;

    public void createLoading(Context context, String message){
        if(contexts != null && contexts.size() > 0){
            if(loadingDialog != null){
                loadingDialog.setMessage(message);
            }
            return;
        }
        loadingDialog = new LoadingDialog(context, message);
        loadingDialog.show();
        loadingDialog.setCancelable(true);
        loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onDestroy();
            }
        });

        if(contexts == null){
            contexts = new ArrayList<>();
        }
        contexts.add(context);
    }

    public void dimissLoading(Context context){
        if(contexts == null){
            return;
        }
        contexts.remove(context);
        if(contexts.size() == 0 && loadingDialog != null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    public void addSubscription(Subscription subscription) {
        if(mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()){
            mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }

    public void onDestroy() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
        dimissLoading(context);
        if (contexts != null){
            contexts.clear();
            contexts = null;
        }
    }

    public boolean hasNet(){
        if(!NetUtils.isNetworkConnected(context)){
            AppUtils.showToast(Application.application.getTopActivity(), R.string.noconnection);
            return false;
        } else {
            return true;
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    class BaseSubscriber<T> extends Subscriber<T> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if(e != null){
                e.printStackTrace();
            }
        }

        @Override
        public void onNext(T o) {

        }
    }

    class BaseLoadingSubscriber<T> extends BaseSubscriber<T> {

        @Override
        public void onCompleted() {
            super.onCompleted();
            dimissLoading(context);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            dimissLoading(context);
        }
    }

    class CreateLoading implements Action0 {

        String message;

        public CreateLoading(Context cxt, String message){
            context = cxt;
            this.message = message;
        }

        @Override
        public void call() {
            createLoading(context, message);
        }
    }

    class DimissLoadingError implements Action1<Throwable> {

        @Override
        public void call(Throwable throwable) {
            if(throwable != null){
                throwable.printStackTrace();
            }
            dimissLoading(context);
        }
    }

    class DimissLoadingCompleted implements Action0 {

        @Override
        public void call() {
            dimissLoading(context);
        }
    }
}
