package com.mango.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.mango.Application;
import com.mango.R;
import com.mango.model.bean.MemberBean;
import com.mango.util.ActivityBuilder;
import com.mango.util.SystemStatusManager;

public class WelcomeActivity extends Activity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemStatusManager.setTranslucentStatusRes(this, android.R.color.transparent);
        setContentView(R.layout.activity_welcome);

        initView();

        initData();
    }

    private void initData() {
        MemberBean member = Application.application.getMember();
        jumpActivity(member);
    }

    private void jumpActivity(MemberBean member){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                if(member == null){
//                    ActivityBuilder.startLoginActivity(WelcomeActivity.this);
//                } else {
//                    if(member.getGender() == null || TextUtils.isEmpty(member.getNick_name())){
//                        ActivityBuilder.startSetNickNameActivity(WelcomeActivity.this);
//                    } else {
                        ActivityBuilder.startMainActivity(WelcomeActivity.this);
//                    }
//                }
                ActivityBuilder.defaultTransition(WelcomeActivity.this);
                finish();
            }
        }, 2000);
    }

    private void initView() {
    }
}
