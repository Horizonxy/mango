package cn.com.mangopi.android.model.data;

import java.util.List;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.ProjectListBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

public class WorksProjectModel {

    public Subscription getProjectList(int pageNo, int relation, Action1 onError, Subscriber<RestResult<List<ProjectListBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainError(ApiManager.getProjectList(pageNo, relation), onError).subscribe(subscriber);
    }
}
