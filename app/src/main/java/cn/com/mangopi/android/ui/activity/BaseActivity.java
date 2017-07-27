package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.baidu.mobstat.StatService;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.BuildConfig;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.util.SystemStatusManager;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Application.application.addActivity(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        SystemStatusManager.setTranslucentStatusColor(this, getResources().getColor(statusColorResId()));
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        SystemStatusManager.setTranslucentStatusColor(this, getResources().getColor(statusColorResId()));
        super.setContentView(view);
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

    public int statusColorResId(){
        return R.color.color_f6f6f7;
    }

}
