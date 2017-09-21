package cn.com.mangopi.android.ui.widget;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.util.AppUtils;

public class MangoUMShareListener implements UMShareListener {

    @Override
    public void onResult(SHARE_MEDIA platform) {
    }

    @Override
    public void onError(SHARE_MEDIA platform, Throwable throwable) {
        AppUtils.showToast(Application.application.getApplicationContext(), platform + " 分享失败");
    }

    @Override
    public void onCancel(SHARE_MEDIA platform) {
    }
}
