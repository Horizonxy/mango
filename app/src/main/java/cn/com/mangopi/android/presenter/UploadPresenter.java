package cn.com.mangopi.android.presenter;


import android.content.Context;

import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.bean.UploadBean;
import cn.com.mangopi.android.model.data.UploadModel;
import cn.com.mangopi.android.ui.viewlistener.UploadViewListener;
import rx.Subscription;

public class UploadPresenter extends BasePresenter {

    UploadModel uploadModel;
    UploadViewListener uploadViewListener;

    public UploadPresenter(UploadModel uploadModel, UploadViewListener uploadViewListener) {
        this.uploadModel = uploadModel;
        this.uploadViewListener = uploadViewListener;
    }

    public void upload(){
        Context context = uploadViewListener.currentContext();
        Subscription subscription =  uploadModel.upload(uploadViewListener.getEntityId(), uploadViewListener.getEntityTypeId(),
                uploadViewListener.getFile(), new CreateLoading(context){
                    @Override
                    public void call() {
                        super.call();
                        uploadViewListener.beforeUpload();
                    }
                }, new BaseLoadingSubscriber<RestResult<UploadBean>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            uploadViewListener.onFailure(e.getMessage());
                        }
                        uploadViewListener.afterUpload(false);
                    }

                    @Override
                    public void onNext(RestResult<UploadBean> restResult) {
                        if(restResult != null){
                            if(restResult.isFailure()){
                                uploadViewListener.onFailure(restResult.getRet_msg());
                                uploadViewListener.afterUpload(false);
                            } else {
                                uploadViewListener.afterUpload(true);
                                uploadViewListener.onSuccess(restResult.getData());
                            }
                        } else {
                            uploadViewListener.afterUpload(false);
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void uploadWithOutLoading(){
        Context context = uploadViewListener.currentContext();
        Subscription subscription =  uploadModel.uploadWithOutLoading(uploadViewListener.getEntityId(), uploadViewListener.getEntityTypeId(),
                uploadViewListener.getFile(), new BaseSubscriber<RestResult<UploadBean>>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(e != null){
                            uploadViewListener.onFailure(e.getMessage());
                        }
                        uploadViewListener.afterUpload(false);
                    }

                    @Override
                    public void onNext(RestResult<UploadBean> restResult) {
                        if(restResult != null){
                            if(restResult.isFailure()){
                                uploadViewListener.onFailure(restResult.getRet_msg());
                                uploadViewListener.afterUpload(false);
                            } else {
                                uploadViewListener.afterUpload(true);
                                uploadViewListener.onSuccess(restResult.getData());
                            }
                        } else {
                            uploadViewListener.afterUpload(false);
                        }
                    }
                });
        addSubscription(subscription);
    }
}
