package cn.com.mangopi.android.presenter;

import android.content.Context;

import cn.com.mangopi.android.model.bean.ContentDetailBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.ContentModel;
import cn.com.mangopi.android.ui.viewlistener.ContentDetailListener;
import rx.Subscription;

public class ContentPresenter extends BasePresenter {

    ContentModel contentModel;
    ContentDetailListener detailListener;

    public ContentPresenter(ContentModel contentModel, ContentDetailListener detailListener) {
        this.contentModel = contentModel;
        this.detailListener = detailListener;
    }

    public void getContent(){
        Context context = detailListener.currentContext();
        Subscription subscription = contentModel.getContent(detailListener.getId(),
                new CreateLoading(context), new BaseLoadingSubscriber<RestResult<ContentDetailBean>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            detailListener.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(RestResult<ContentDetailBean> restResult) {
                        if(restResult != null){
                            if(restResult.isSuccess()){
                                detailListener.onSuccess(restResult.getData());
                            }else {
                                detailListener.onFailure(restResult.getRet_msg());
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
}
