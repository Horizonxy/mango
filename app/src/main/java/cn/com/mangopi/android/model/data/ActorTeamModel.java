package cn.com.mangopi.android.model.data;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.ActorTeamBean;
import cn.com.mangopi.android.model.bean.ProjectActorBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class ActorTeamModel {

    public Subscription getActorTeam(long id, Action0 onSubscribe, Subscriber<RestResult<ActorTeamBean>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getActorTeam(id), onSubscribe).subscribe(subscriber);
    }

    public Subscription getProjectActor(long id, Action0 onSubscribe, Subscriber<RestResult<ProjectActorBean>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getProjectActor(id), onSubscribe).subscribe(subscriber);
    }
}
