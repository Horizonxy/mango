package cn.com.mangopi.android.presenter;

import android.content.Context;

import java.util.List;

import cn.com.mangopi.android.model.bean.MessageBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.MessageModel;
import cn.com.mangopi.android.ui.viewlistener.MessageListener;
import rx.Subscription;
import rx.functions.Action1;

public class MessagePresenter extends BasePresenter {

    MessageModel messageModel;
    MessageListener messageListener;

    public MessagePresenter(MessageModel messageModel, MessageListener messageListener) {
        this.messageModel = messageModel;
        this.messageListener = messageListener;
    }

    public void getMessageList(){
        Subscription subscription = messageModel.getMessageList(messageListener.getPageNo(),
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                        messageListener.onFailure(null);
                    }
                }, new BaseSubscriber<RestResult<List<MessageBean>>>(){
                    @Override
                    public void onNext(RestResult<List<MessageBean>> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                messageListener.onSuccess(restResult.getData());
                            } else {
                                messageListener.onFailure(restResult.getRet_msg());
                            }
                        } else {
                            messageListener.onFailure(null);
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void getMessageCheck(){
        Subscription subscription = messageModel.getMessageCheck(new BaseSubscriber<RestResult<String>>(){
                    @Override
                    public void onNext(RestResult<String> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                messageListener.onHasMessage(true);
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
