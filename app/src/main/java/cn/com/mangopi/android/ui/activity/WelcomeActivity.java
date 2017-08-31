package cn.com.mangopi.android.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.MemberBean;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.presenter.MemberPresenter;
import cn.com.mangopi.android.ui.viewlistener.MemberDetailListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.SystemStatusManager;

public class WelcomeActivity extends Activity implements MemberDetailListener {

    private Handler handler = new Handler();
    MemberBean member;
    MemberPresenter memberPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemStatusManager.setTranslucentStatusRes(this, android.R.color.transparent);
        setContentView(R.layout.activity_welcome);

        initView();

        initData();
    }

    private void initData() {
        member = Application.application.getMember();
        if(member == null){
            jumpActivity(member);
        } else {
            memberPresenter = new MemberPresenter(new MemberModel(), this);
            memberPresenter.getMember();
        }
    }

    private void jumpActivity(MemberBean member){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(member == null){
                    ActivityBuilder.startLoginActivity(WelcomeActivity.this);
                } else {
                    if(member.getGender() == null || TextUtils.isEmpty(member.getNick_name())){
                        ActivityBuilder.startSetNickNameActivity(WelcomeActivity.this);
                    } else {
                        ActivityBuilder.startMainActivity(WelcomeActivity.this);
                    }
                }
                ActivityBuilder.defaultTransition(WelcomeActivity.this);
                finish();
            }
        }, 2000);
    }

    private void initView() {
    }

    @Override
    public void onFailure(String message) {
        jumpActivity(member);
    }

    @Override
    public Context currentContext() {
        return this;
    }

    @Override
    public void onSuccess(MemberBean member) {
        this.member = member;
        Application.application.saveMember(member, Application.application.getSessId());
        jumpActivity(member);
    }

    @Override
    public long getMemberId() {
        if(member != null){
            return member.getId();
        }
        return 0;
    }
}
