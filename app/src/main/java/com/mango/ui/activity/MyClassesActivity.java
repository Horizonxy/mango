package com.mango.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.mango.R;

public class MyClassesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes);

        initView();
    }

    private void initView() {

    }
}
