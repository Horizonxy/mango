package cn.com.mangopi.android.model.data;

import java.util.List;
import java.util.Map;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.ProjectDetailBean;
import cn.com.mangopi.android.model.bean.ProjectJoinBean;
import cn.com.mangopi.android.model.bean.ProjectListBean;
import cn.com.mangopi.android.model.bean.ProjectTeamBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

public class WorksProjectModel {

    public Subscription getProjectList(int pageNo, int relation, Action1 onError, Subscriber<RestResult<List<ProjectListBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainError(ApiManager.getProjectList(pageNo, relation), onError).subscribe(subscriber);
    }

    public Subscription getProject(long id, Action0 onSubscribe, Subscriber<RestResult<ProjectDetailBean>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getProject(id), onSubscribe).subscribe(subscriber);
    }

    public Subscription projectJoin(Map<String, Object> map, Action0 onSubscribe, Subscriber<RestResult<ProjectJoinBean>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.projectJoin(map), onSubscribe).subscribe(subscriber);
    }

    public Subscription applyProjectTeam(Map<String, Object> map, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.applyProjectTeam(map), onSubscribe).subscribe(subscriber);
    }

    public Subscription projectTeamList(long id, Action0 onSubscribe, Subscriber<RestResult<List<ProjectTeamBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.projectTeamList(id), onSubscribe).subscribe(subscriber);
    }

    public Subscription actorComment(long id, int score, String comment, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.actorComment(id, score, comment), onSubscribe).subscribe(subscriber);
    }
}
