package com.mango.presenter;

import android.content.Context;
import android.content.DialogInterface;

import com.mango.ui.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
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
        context = null;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
