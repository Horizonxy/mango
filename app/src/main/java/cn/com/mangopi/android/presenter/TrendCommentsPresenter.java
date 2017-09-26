package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.model.bean.ReplyTrendBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.bean.TrendDetailBean;
import cn.com.mangopi.android.model.data.TrendModel;
import cn.com.mangopi.android.ui.viewlistener.BaseViewListener;
import cn.com.mangopi.android.ui.viewlistener.FoundListener;
import cn.com.mangopi.android.ui.viewlistener.TrendCommentsListener;
import rx.Subscription;

public class TrendCommentsPresenter extends BasePresenter {

    TrendModel trendModel;
    BaseViewListener baseListener;

    public TrendCommentsPresenter(BaseViewListener baseListener) {
        this.baseListener = baseListener;
        this.trendModel = new TrendModel();
    }

    public void getTrend(){
        TrendCommentsListener trendCommentsListener = (TrendCommentsListener) baseListener;
        Context context = trendCommentsListener.currentContext();
        Subscription subscription = trendModel.getTrend(trendCommentsListener.getId(), new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<TrendDetailBean>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            trendCommentsListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<TrendDetailBean> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                trendCommentsListener.onTrendSuccess(restResult.getData());
                            } else {
                                trendCommentsListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void replyTrend(){
        FoundListener foundListener = (FoundListener) baseListener;
        Context context = foundListener.currentContext();
        Subscription subscription = trendModel.replyTrend(foundListener.replyTrendMap(), new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<ReplyTrendBean>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            foundListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<ReplyTrendBean> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                foundListener.onReplyTrendSuccess(restResult.getData());
                            } else {
                                foundListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void replyComment(){
        TrendCommentsListener trendCommentsListener = (TrendCommentsListener) baseListener;
        Context context = trendCommentsListener.currentContext();
        Subscription subscription = trendModel.replyTrend(trendCommentsListener.replyCommentMap(), new CreateLoading(context),
                new BaseLoadingSubscriber<RestResult<ReplyTrendBean>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            trendCommentsListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<ReplyTrendBean> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                trendCommentsListener.onReplyCommentSuccess(restResult.getData());
                            } else {
                                trendCommentsListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
