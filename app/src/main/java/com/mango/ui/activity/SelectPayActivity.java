package com.mango.ui.activity;

import android.os.Bundle;

import com.mango.R;

public class SelectPayActivity extends BaseTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pay);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.pay_for_order);

    }
}
