package cn.com.mangopi.android.presenter;

import android.content.Context;

import java.util.List;

import cn.com.mangopi.android.model.bean.CourseBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.CourseModel;
import cn.com.mangopi.android.ui.viewlistener.CourseListListener;
import rx.Subscription;
import rx.functions.Action1;

public class CourseListPresenter extends BasePresenter {

    CourseModel courseModel;
    CourseListListener courseListListener;

    public CourseListPresenter(CourseModel courseModel, CourseListListener courseListListener) {
        this.courseModel = courseModel;
        this.courseListListener = courseListListener;
    }

    public void getCourseList(){
        Subscription subscription = courseModel.getCourseList(courseListListener.getQueryMap(),
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                        courseListListener.onFailure(null);
                    }
                }, new BaseSubscriber<RestResult<List<CourseBean>>>(){
                    @Override
                    public void onNext(RestResult<List<CourseBean>> restResult) {
                        super.onNext(restResult);
                        if (restResult == null) {
                            courseListListener.onFailure(null);
                        } else {
                            if (restResult.isSuccess()) {
                                courseListListener.onSuccess(restResult.getData());
                            } else {
                                courseListListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void delCourse(CourseBean course){
        Context context = courseListListener.currentContext();
        Subscription subscription = courseModel.delCourse(course.getId(),
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            courseListListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null) {
                            if (restResult.isSuccess()) {
                                courseListListener.onDelSuccess(course);
                            } else {
                                courseListListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void offCourse(CourseBean course){
        Context context = courseListListener.currentContext();
        Subscription subscription = courseModel.offCourse(course.getId(),
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            courseListListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()) {
                                courseListListener.onOffSuccess(course);
                            } else {
                                courseListListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void onCourse(CourseBean course){
        Context context = courseListListener.currentContext();
        Subscription subscription = courseModel.onCourse(course.getId(),
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            courseListListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()) {
                                courseListListener.onOnSuccess(course);
                            } else {
                                courseListListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
