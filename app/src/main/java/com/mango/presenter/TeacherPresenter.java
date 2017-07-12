package com.mango.presenter;

import android.content.Context;

import com.mango.model.bean.CourseBean;
import com.mango.model.bean.CourseClassifyBean;
import com.mango.model.bean.RestResult;
import com.mango.model.data.CourseModel;
import com.mango.ui.viewlistener.TeacherListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;
import rx.functions.Action1;

public class TeacherPresenter extends BasePresenter {

    CourseModel courseModel;
    TeacherListener listener;

    public TeacherPresenter(CourseModel courseModel, TeacherListener listener) {
        this.courseModel = courseModel;
        this.listener = listener;
    }

    public void getCourseList(int hotTypes){
        Context context = listener.currentContext();
        Subscription subscription = courseModel.getCourseList(listener.getQueryMap(hotTypes), new Action1<Throwable>(){

            @Override
            public void call(Throwable e) {
                if (e != null) {
                    e.printStackTrace();
                }
                listener.onFailure(null);
            }
        } ,new BaseSubscriber<RestResult<List<CourseBean>>>(){
            @Override
            public void onNext(RestResult<List<CourseBean>> restResult) {
                if (restResult == null) {
                    listener.onFailure(null);
                } else {
                    if (restResult.isSuccess()) {
                        listener.onCourseListSuccess(hotTypes, restResult.getData());
                    } else {
                        listener.onFailure(null);
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    public void getCourseClassify(){
        Context context = listener.currentContext();
        Map<String, Long> map = new HashMap<String, Long>();
        map.put("show_top", 1L);
        Subscription subscription = courseModel.getClassify(map, new BaseSubscriber<RestResult<List<CourseClassifyBean>>>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(e != null) {
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onNext(RestResult<List<CourseClassifyBean>> restResult) {
                if(restResult != null && restResult.isSuccess()){
                    listener.onClassifySuccess(restResult.getData());
                }
            }
        });
        addSubscription(subscription);
    }
}
