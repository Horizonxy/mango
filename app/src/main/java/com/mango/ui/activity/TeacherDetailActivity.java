package com.mango.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.Constants;
import com.mango.R;
import com.mango.ui.widget.ListView;
import butterknife.Bind;
import butterknife.OnClick;

public class TeacherDetailActivity extends BaseTitleBarActivity {

    ImageView ivLogo;
    TextView tvCity;
    TextView tvName;
    TextView tvJob;
    TextView tvWantCount;
    TextView tvWantedCount;
    @Bind(R.id.lv_courses)
    ListView lvCourses;
    @Bind(R.id.iv_want)
    ImageView ivWant;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);

        id = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);
        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.teacher_detail);

        View header = getLayoutInflater().inflate(R.layout.layout_header_teacher_detail, null, false);
        ivLogo = (ImageView) header.findViewById(R.id.iv_logo);
        tvName = (TextView) header.findViewById(R.id.tv_name);
        tvJob = (TextView) header.findViewById(R.id.tv_jobs);
        tvCity = (TextView) header.findViewById(R.id.tv_city);
        tvWantCount = (TextView) header.findViewById(R.id.tv_want_count);
        tvWantedCount = (TextView) header.findViewById(R.id.tv_wanted_count);

        lvCourses.addHeaderView(header);
    }

    @OnClick(R.id.btn_listen)
    void listenClick(){

    }
}
