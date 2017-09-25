package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.ui.fragment.ClassListFragment;

public class CourseListActivity extends BaseTitleBarActivity {

    CourseClassifyBean classify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        classify = (CourseClassifyBean) getIntent().getSerializableExtra(Constants.BUNDLE_CLASSIFY);
        initView();
    }

    private void initView() {
        titleBar.setTitle(classify.getClassify_name());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ClassListFragment courseListFragment = ClassListFragment.newInstance(classify);
        ft.add(R.id.content, courseListFragment);
        ft.commit();
    }
}
