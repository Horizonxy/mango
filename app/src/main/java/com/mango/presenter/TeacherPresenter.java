package com.mango.presenter;

import com.mango.model.bean.CommonBean;
import com.mango.model.bean.CourseBean;
import com.mango.model.bean.CourseClassifyBean;
import com.mango.model.bean.RestResult;
import com.mango.model.data.CourseModel;
import com.mango.model.db.CommonDaoImpl;
import com.mango.ui.viewlistener.TeacherListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Subscription;
import rx.functions.Action1;

public class TeacherPresenter extends BasePresenter {

    CourseModel courseModel;
    TeacherListener listener;
    CommonDaoImpl commonDao;
    String tutorCorseListKey = null;

    public TeacherPresenter(CourseModel courseModel, TeacherListener listener) {
        this.courseModel = courseModel;
        this.listener = listener;
        this.commonDao = new CommonDaoImpl(listener.currentContext());
    }

    public void getCourseList(int hotTypes){
        Map<String, Object> map = listener.getQueryMap(hotTypes);
        boolean cache = (int)map.get("page_no") == 1;
        if(cache){
            tutorCorseListKey = "tutor_corse_list_" + hotTypes;
            List<CommonBean> list = commonDao.findByColumn(CommonBean.DATA_TYPE, tutorCorseListKey);
            if(list != null && list.size() > 0){
                List<CourseBean> data = (List<CourseBean>) list.get(0).getData();
                listener.onCourseListSuccess(hotTypes, data);
            }
        }

        Subscription subscription = courseModel.getCourseList(map, new Action1<Throwable>(){

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
                        if(cache){
                            commonDao.saveData(tutorCorseListKey, (Serializable) restResult.getData());
                        }
                    } else {
                        listener.onFailure(null);
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    public void getCourseClassify(){
        String key = "tutor_corse_classify_list";
        List<CommonBean> list = commonDao.findByColumn(CommonBean.DATA_TYPE, key);
        if(list != null && list.size() > 0){
            List<CourseClassifyBean> data = (List<CourseClassifyBean>) list.get(0).getData();
            listener.onClassifySuccess(data);
        }

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

                    commonDao.saveData(key, (Serializable) restResult.getData());
                }
            }
        });
        addSubscription(subscription);
    }
}
