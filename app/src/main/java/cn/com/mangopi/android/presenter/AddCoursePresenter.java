package cn.com.mangopi.android.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.model.bean.CourseTypeBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.CourseModel;
import cn.com.mangopi.android.ui.viewlistener.AddCourseLisetener;
import rx.Subscription;

public class AddCoursePresenter extends BasePresenter {

    CourseModel courseModel;
    AddCourseLisetener addCourseLisetener;

    public AddCoursePresenter(CourseModel courseModel, AddCourseLisetener addCourseLisetener) {
        this.courseModel = courseModel;
        this.addCourseLisetener = addCourseLisetener;
    }

    public void getClassify(){
        Context context = addCourseLisetener.currentContext();
        Map<String, Long> map = new HashMap<>();
        Subscription subscription = courseModel.getClassify(map, new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<ArrayList<CourseClassifyBean>>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            addCourseLisetener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<ArrayList<CourseClassifyBean>> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                addCourseLisetener.onClassifySuccess(restResult.getData());
                            } else {
                                addCourseLisetener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void getTypeList(){
        Context context = addCourseLisetener.currentContext();
        Subscription subscription = courseModel.getTypeList(new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<ArrayList<CourseTypeBean>>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            addCourseLisetener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<ArrayList<CourseTypeBean>> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                addCourseLisetener.onTypeSuccess(restResult.getData());
                            } else {
                                addCourseLisetener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
