package com.mango.ui.activity;

import android.os.Bundle;

import com.mango.R;

public class MyAccountActivity extends BaseTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        initView();
    }

    private void initView() {
        titleBar.setTitle(getString(R.string.my_account));
    }
}
