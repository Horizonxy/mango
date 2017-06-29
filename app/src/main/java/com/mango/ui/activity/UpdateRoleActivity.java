package com.mango.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.mango.R;

public class UpdateRoleActivity extends BaseTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_role);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.update_my_role);
    }
}
