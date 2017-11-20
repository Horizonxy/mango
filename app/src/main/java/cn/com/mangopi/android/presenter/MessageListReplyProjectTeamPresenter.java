package cn.com.mangopi.android.presenter;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.WorksProjectModel;
import cn.com.mangopi.android.ui.viewlistener.BaseViewListener;
import rx.Subscription;

public class MessageListReplyProjectTeamPresenter extends BasePresenter {

    WorksProjectModel projectModel;
    BaseViewListener viewListener;

    public MessageListReplyProjectTeamPresenter(BaseViewListener viewListener) {
        this.projectModel = new WorksProjectModel();
        this.viewListener = viewListener;
    }

    public void replyJoinTeam(long messageId, int isReject, String rejectReason){
        Map<String, Object> map = new HashMap<>();
        map.put("message_id", messageId);
        map.put("is_reject", isReject);
        if(1 == isReject) {
            map.put("reject_reason", rejectReason);
        }
        Subscription subscription = projectModel.replyProjectTeam(map, new CreateLoading(viewListener.currentContext()),
                new BaseLoadingSubscriber<RestResult<Object>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            viewListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<Object> restResult) {

                    }
                });
        addSubscription(subscription);
    }
}
