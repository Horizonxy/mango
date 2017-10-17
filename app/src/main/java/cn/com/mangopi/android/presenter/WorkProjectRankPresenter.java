package cn.com.mangopi.android.presenter;

import java.util.List;

import cn.com.mangopi.android.model.bean.ProjectActorBean;
import cn.com.mangopi.android.model.bean.ProjectDetailBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.ActorTeamModel;
import cn.com.mangopi.android.ui.viewlistener.WorkProjectRankListener;
import cn.com.mangopi.android.util.SimpleSubscriber;
import rx.Subscription;

public class WorkProjectRankPresenter extends BasePresenter {

    WorkProjectRankListener rankListener;
    ActorTeamModel actorTeamModel;

    public WorkProjectRankPresenter(WorkProjectRankListener rankListener) {
        this.rankListener = rankListener;
        this.actorTeamModel = new ActorTeamModel();
    }

    public void getRankList(List<ProjectDetailBean.ProjectActorBean> actors){
        Subscription subscription = actorTeamModel.getRankProjectActors(actors, new SimpleSubscriber<List<ProjectActorBean>>() {
            @Override
            public void onNextRx(List<ProjectActorBean> rankList) {
                if(rankList != null){
                    rankListener.onRankList(rankList);
                }
            }
        });
        addSubscription(subscription);

    }
}
