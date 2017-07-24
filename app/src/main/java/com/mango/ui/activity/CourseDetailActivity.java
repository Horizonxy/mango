package com.mango.ui.activity;

import android.os.Bundle;

import com.mango.Constants;
import com.mango.R;

public class CourseDetailActivity extends BaseTitleBarActivity {

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        id = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);
        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.course_detail);
    }
}
