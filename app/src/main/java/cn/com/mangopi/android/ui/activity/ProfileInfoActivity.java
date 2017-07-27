package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.MemberBean;

public class ProfileInfoActivity extends BaseTitleBarActivity {

    MemberBean member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        member = Application.application.getMember();
        if(member == null){
            finish();
        }
        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.profile_info);
    }

}
