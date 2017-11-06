package cn.com.mangopi.android.presenter;

import cn.com.mangopi.android.model.bean.AppSignBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.data.AppVisionModel;
import cn.com.mangopi.android.ui.viewlistener.AppVisionListener;
import cn.com.mangopi.android.util.SimpleSubscriber;
import rx.Subscription;

public class AppVisionPresenter extends BasePresenter {

    private AppVisionModel appVisionModel;
    private AppVisionListener appVisionListener;

    public AppVisionPresenter(AppVisionListener appVisionListener) {
        this.appVisionListener = appVisionListener;
        this.appVisionModel = new AppVisionModel();
    }

    public void appSign(){
        Subscription subscription = appVisionModel.appSign(appVisionListener.getAppSignMap(), new SimpleSubscriber<RestResult<AppSignBean>>() {
            @Override
            public void onNextRx(RestResult<AppSignBean> restResult) {
                if(restResult != null && restResult.isSuccess() && restResult.getData() != null){
                    appVisionListener.onAppSignSuccess(restResult.getData());
                }
            }
        });
        addSubscription(subscription);
    }
}
