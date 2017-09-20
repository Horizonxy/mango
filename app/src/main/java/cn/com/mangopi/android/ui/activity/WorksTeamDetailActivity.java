package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import cn.com.mangopi.android.R;

/**
 * 工作包 团队
 */
public class WorksTeamDetailActivity extends BaseTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works_team_detail);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.works_team);
    }
}
