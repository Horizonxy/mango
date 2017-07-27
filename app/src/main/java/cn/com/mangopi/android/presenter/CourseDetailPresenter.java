package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.bean.CourseDetailBean;
import cn.com.mangopi.android.model.data.CourseModel;
import cn.com.mangopi.android.ui.viewlistener.CourseDetailListener;

import rx.Subscription;

public class CourseDetailPresenter extends BasePresenter {

    CourseModel courseModel;
    CourseDetailListener listener;

    public CourseDetailPresenter(CourseModel courseModel, CourseDetailListener listener) {
        this.courseModel = courseModel;
        this.listener = listener;
    }

    public void getCourse(){
        Context context = listener.currentContext();
        Subscription subscription = courseModel.getCourse(listener.getId(), new CreateLoading(context), new BaseLoadingSubscriber<RestResult<CourseDetailBean>>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null){
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<CourseDetailBean> restResult) {
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
