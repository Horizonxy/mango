package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import cn.com.mangopi.android.R;

/**
 * 我的工作包
 */
public class MemberWorksActivity extends BaseTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pull_listview);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.my_join_works);
    }
}
