package com.mango.presenter;

import android.content.Context;

import com.mango.R;
import com.mango.model.bean.RegistBean;
import com.mango.model.bean.RestResult;
import com.mango.model.bean.TutorBean;
import com.mango.model.data.TutorModel;
import com.mango.ui.viewlistener.TutorDetailListener;

import rx.Subscription;

public class TutorDetailPresenter extends BasePresenter {

    private TutorModel tutorModel;
    private TutorDetailListener listener;

    public TutorDetailPresenter(TutorModel tutorModel, TutorDetailListener listener) {
        this.tutorModel = tutorModel;
        this.listener = listener;
    }

    public void getTutor(){
        Context context = listener.currentContext();
        Subscription subscription = tutorModel.getTutor(listener.getId(), new CreateLoading(context), new BaseLoadingSubscriber<RestResult<TutorBean>>(){

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null){
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<TutorBean> restResult) {
                if(restResult != null){
                    if(restResult.isSuccess()){
                        listener.onSuccess(restResult.getData());
                    } else {
                        listener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }
}
