package cn.com.mangopi.android.model.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.ActorTeamBean;
import cn.com.mangopi.android.model.bean.ProjectActorBean;
import cn.com.mangopi.android.model.bean.ProjectDetailBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.util.RxJavaUtils;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
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

    public Subscription getRankProjectActors(List<ProjectDetailBean.ProjectActorBean> actors, Subscriber<List<ProjectActorBean>> subscriber){
        return RxJavaUtils.schedulersIoMain(Observable.create(new Observable.OnSubscribe<List<ProjectActorBean>>() {
            @Override
            public void call(Subscriber<? super List<ProjectActorBean>> subscriber) {
                int current = 0;
                List<ProjectActorBean> ranks = new ArrayList<>();
                for (int i = 0; actors != null && i < actors.size() && current < 3; i++){
                    Call<RestResult<ProjectActorBean>> actor = Application.application.getApiService().getProjectActorSync(actors.get(i).getId());
                    try {
                        Response<RestResult<ProjectActorBean>> response = actor.execute();
                        RestResult<ProjectActorBean> body = response.body();
                        if(body != null && body.isSuccess() && body.getData() != null && body.getData().getIntegrated_ranking() > 0){
                            current++;
                            ranks.add(body.getData());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onNext(ranks);
                subscriber.onCompleted();
            }
        })).subscribe(subscriber);
    }
}
