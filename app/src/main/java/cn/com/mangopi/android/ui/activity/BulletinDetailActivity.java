package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;

import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.BulletinBean;
import cn.com.mangopi.android.ui.widget.web.WebView;
import cn.com.mangopi.android.util.MangoUtils;

import butterknife.Bind;

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

    @Override
    public int statusColorResId() {
        return R.color.color_ffb900;
    }
}