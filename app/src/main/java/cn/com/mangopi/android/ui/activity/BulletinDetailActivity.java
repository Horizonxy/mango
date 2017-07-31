package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import android.view.View;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.BulletinBean;
import cn.com.mangopi.android.ui.widget.web.MangoWebView;
import cn.com.mangopi.android.util.MangoUtils;

public class BulletinDetailActivity extends BaseTitleBarActivity {

    @Bind(R.id.web_view)
    MangoWebView webView;

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
        titleBar.setBarBackGroundColor(R.color.color_ffb900);
    }

    @Override
    public int statusColorResId() {
        return R.color.color_ffb900;
    }

    @OnClick(R.id.ib_back)
    void onBackClick(View v){
        finish();
    }
}
