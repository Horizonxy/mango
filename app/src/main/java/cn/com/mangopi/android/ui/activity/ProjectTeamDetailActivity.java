package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import android.view.View;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.widget.TitleBar;

public class ProjectTeamDetailActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_team_detail);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.works_team);
        titleBar.setRightText(R.string.save);
    }

    @Override
    public void onTitleButtonClick(View view) {
        switch (view.getId()){
            case R.id.tv_right:
                break;
        }
    }
}
