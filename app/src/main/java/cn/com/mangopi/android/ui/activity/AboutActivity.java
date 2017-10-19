package cn.com.mangopi.android.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import cn.com.mangopi.android.R;

public class AboutActivity extends BaseTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.about_us);
    }
}
