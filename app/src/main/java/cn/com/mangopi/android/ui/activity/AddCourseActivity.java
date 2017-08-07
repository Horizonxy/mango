package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;

import cn.com.mangopi.android.R;

public class AddCourseActivity extends BaseTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.add_course);
    }
}
