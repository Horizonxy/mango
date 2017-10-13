package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.adapter.ProjectActorPhotoAdapter;
import cn.com.mangopi.android.ui.widget.GridView;
import cn.com.mangopi.android.util.DisplayUtils;

public class WorkProjectTeamDocActivity extends BaseTitleBarActivity {

    @Bind(R.id.gv_team_doc)
    GridView gvTeamDoc;
    @Bind(R.id.layout_empty)
    View emptyView;
    @Bind(R.id.tv_content_empty_tip)
    TextView tvDocPhotoTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_project_actor_doc);

        initView();
    }

    private void initView() {
        tvDocPhotoTip.setText("暂无方案文档");
        String title = getIntent().getStringExtra(Constants.BUNDLE_TITLE);
        titleBar.setTitle(title);
        List<String> docRsurls= (List<String>) getIntent().getSerializableExtra(Constants.BUNDLE_DATA);
        if(docRsurls == null){
            docRsurls = new ArrayList<String>();
        }

        int width = (DisplayUtils.screenWidth(this) - DisplayUtils.dip2px(this, 15 * 2 + 10 * 3)) / 4;
        gvTeamDoc.setAdapter(new ProjectActorPhotoAdapter(this, R.layout.gridview_item_image, docRsurls, width));
        gvTeamDoc.setEmptyView(emptyView);
    }
}
