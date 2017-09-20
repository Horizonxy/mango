package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import cn.com.mangopi.android.R;

/**
 * 工作包作品
 */
public class WorksCollectionActivity extends BaseTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works_collection);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.works_collection);
    }

}
