package com.mango.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.mango.Constants;
import com.mango.R;
import com.mango.model.bean.BulletinBean;
import com.mango.ui.widget.web.WebView;
import com.mango.util.MangoUtils;
import com.mango.util.SystemStatusManager;

import butterknife.Bind;
import butterknife.OnClick;

public class BulletinDetailActivity extends BaseTitleBarActivity {

    @Bind(R.id.web_view)
    WebView webView;

    //公告
    BulletinBean bulletin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_detail);


        bulletin = (BulletinBean) getIntent().getSerializableExtra(Constants.BUNDLE_BULLETIN);
        if(bulletin != null){
            titleBar.setLeftText(bulletin.getTitle());
            webView.loadData(MangoUtils.makeHtml(bulletin.getContent()), "text/html; charset=UTF-8", null);
        }
    }

    @OnClick(R.id.ib_back)
    void backClick(){
        finish();
    }

    @Override
    public int statusColorResId() {
        return R.color.color_ffb900;
    }
}
