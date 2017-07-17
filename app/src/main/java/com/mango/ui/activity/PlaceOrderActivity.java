package com.mango.ui.activity;

import android.os.Bundle;

import com.mango.R;

public class PlaceOrderActivity extends BaseTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.place_order);
    }
}
