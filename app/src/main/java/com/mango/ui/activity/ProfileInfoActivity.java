package com.mango.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mango.R;

import butterknife.OnClick;

public class ProfileInfoActivity extends BaseTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.profile_info);
    }

}
