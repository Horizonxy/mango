package com.mango.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mobstat.StatService;
import com.mango.Application;
import com.mango.BuildConfig;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Application.application.addActivity(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        Application.application.removeActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!BuildConfig.DEBUG) {
            StatService.onPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!BuildConfig.DEBUG) {
            StatService.onResume(this);
        }
    }

}
