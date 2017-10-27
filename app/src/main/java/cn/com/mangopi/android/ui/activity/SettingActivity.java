package cn.com.mangopi.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.popupwindow.SharePopupWindow;
import cn.com.mangopi.android.ui.widget.LoadingDialog;
import cn.com.mangopi.android.ui.widget.MangoUMShareListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.DataCleanManager;
import cn.com.mangopi.android.util.FileUtils;
import cn.com.mangopi.android.util.SetTransPwdDialog;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.util.SimpleSubscriber;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SettingActivity extends BaseTitleBarActivity {

    @Bind(R.id.layout_profile_info)
    View vProfileInfo;
    @Bind(R.id.layout_phone_num)
    View vPhoneNum;
    @Bind(R.id.layout_trans_pwd)
    View vTransPwd;
    @Bind(R.id.layout_about_us)
    View vAboutUs;
    @Bind(R.id.layout_share_to_friend)
    View vShareToFriend;
    @Bind(R.id.layout_clear_cache)
    View vClearCache;
    LoadingDialog loadingDialog = null;

    TextView tvProfileLeft, tvProfileRight;
    TextView tvPhoneNunLeft, tvPhoneNunRight;
    TextView tvTransPwdLeft, tvTransPwdRight;
    TextView tvAboutUsLeft, tvAboutUsRight;
    TextView tvShareToLeft, tvShareToRight;
    TextView tvClearCacheft, tvClearCacheRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        titleBar.setTitle(getString(R.string.setting));

        tvProfileLeft = (TextView) vProfileInfo.findViewById(R.id.tv_left);
        tvProfileRight = (TextView) vProfileInfo.findViewById(R.id.tv_right);
        tvPhoneNunLeft = (TextView) vPhoneNum.findViewById(R.id.tv_left);
        tvPhoneNunRight = (TextView) vPhoneNum.findViewById(R.id.tv_right);
        vPhoneNum.findViewById(R.id.iv_right).setVisibility(View.GONE);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvPhoneNunRight.getLayoutParams();
        params.rightMargin = (int) getResources().getDimension(R.dimen.dp_15);
        tvPhoneNunRight.setLayoutParams(params);
        tvTransPwdLeft = (TextView) vTransPwd.findViewById(R.id.tv_left);
        tvTransPwdRight = (TextView) vTransPwd.findViewById(R.id.tv_right);
        tvAboutUsLeft = (TextView) vAboutUs.findViewById(R.id.tv_left);
        tvAboutUsRight = (TextView) vAboutUs.findViewById(R.id.tv_right);
        tvShareToLeft = (TextView) vShareToFriend.findViewById(R.id.tv_left);
        tvShareToRight = (TextView) vShareToFriend.findViewById(R.id.tv_right);
        tvClearCacheft = (TextView) vClearCache.findViewById(R.id.tv_left);
        tvClearCacheRight = (TextView) vClearCache.findViewById(R.id.tv_right);

        tvProfileLeft.setText(getString(R.string.profile_info));
        tvProfileRight.setText(getString(R.string.click_to_edit));
        tvPhoneNunLeft.setText(getString(R.string.phone_num));
        if(Application.application.getMember() != null) {
            tvPhoneNunRight.setText(Application.application.getMember().getMobile());
        }
        tvPhoneNunRight.setTextColor(getResources().getColor(R.color.color_666666));
        tvTransPwdLeft.setText(getString(R.string.trans_pwd));
        tvTransPwdRight.setText(getString(R.string.un_setting));
        tvAboutUsLeft.setText(getString(R.string.about_us));
        tvShareToLeft.setText(getString(R.string.share_to_friend));
        tvClearCacheft.setText(getString(R.string.clear_cache));

        tvClearCacheRight.setText(DataCleanManager.getFormatSize(getCacheSize()));
    }

    @OnClick(R.id.btn_login_out)
    void loginOut(View v) {
        ActivityBuilder.startLoginActivity(this);
    }

    @OnClick(R.id.layout_profile_info)
    void profileClick(View v) {
        ActivityBuilder.startProfileInfoActivity(this);
    }

    @OnClick(R.id.layout_about_us)
    void aboutClick(View v){
        startActivity(new Intent(this, AboutActivity.class));
    }

    @OnClick(R.id.layout_clear_cache)
    void clearCacheClick(View v) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                DataCleanManager.cleanFiles(SettingActivity.this);
                DataCleanManager.cleanInternalCache(SettingActivity.this);
                DataCleanManager.cleanCustomCache(Constants.BASE_DIR);

                subscriber.onNext(DataCleanManager.getFormatSize(getCacheSize()));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
            @Override
            public void call() {
                loadingDialog = new LoadingDialog(SettingActivity.this, getResources().getString(R.string.please_wait));
                loadingDialog.show();
                loadingDialog.setCancelable(true);
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new SimpleSubscriber<String>() {
            @Override
            public void onNextRx(String obj) {
                if(loadingDialog != null && loadingDialog.isShowing()){
                    loadingDialog.dismiss();
                }
                tvClearCacheRight.setText(obj);
            }

            @Override
            public void onError(Throwable e) {
                if(loadingDialog != null && loadingDialog.isShowing()){
                    loadingDialog.dismiss();
                }
            }
        });

    }

    private long getCacheSize() {
        long size = 0;
        try {
            long cacheDir = DataCleanManager.getFolderSize(getCacheDir());
            size = size + cacheDir;
            long fileDir = DataCleanManager.getFolderSize(getFilesDir());
            size = size + fileDir;
            File sd = new File(Constants.BASE_DIR);
            if (sd != null && sd.exists()) {
                long sdDir = DataCleanManager.getFolderSize(sd);
                size = size + sdDir;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    @OnClick(R.id.layout_trans_pwd)
    void setTransPwd(View v) {
        new SetTransPwdDialog(this, new SetTransPwdDialog.onPwdFinishListener() {
            @Override
            public void onFinish(String pwd) {
                AppUtils.showToast(SettingActivity.this, pwd);
            }
        }).show();
    }

    @OnClick(R.id.layout_share_to_friend)
    void shareToFriend(View v){
        SharePopupWindow sharePopupWindow = new SharePopupWindow(this, "https://www.mangopi.com.cn/", "π的世界，伴你创造无限可能。",
                "乐享芒果派", null, new MangoUMShareListener());
        sharePopupWindow.show();
    }
}
