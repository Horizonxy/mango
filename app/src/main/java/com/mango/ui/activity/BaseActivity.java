package com.mango.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.mango.Application;

import butterknife.ButterKnife;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        Application.application.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        Application.application.removeActivity(this);
    }
}
