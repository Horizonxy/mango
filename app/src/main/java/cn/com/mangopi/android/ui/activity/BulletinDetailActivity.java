package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.BulletinBean;
import cn.com.mangopi.android.model.data.BulletinModel;
import cn.com.mangopi.android.presenter.BulletinPresenter;
import cn.com.mangopi.android.ui.viewlistener.BulletinDetailListener;
import cn.com.mangopi.android.ui.widget.web.MangoWebView;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.MangoUtils;

public class BulletinDetailActivity extends BaseTitleBarActivity implements BulletinDetailListener {

    @Bind(R.id.web_view)
    MangoWebView webView;

    long id;
    BulletinPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_detail);

        id = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);
        titleBar.setBarBackGroundColor(R.color.color_ffb900);

        presenter = new BulletinPresenter(new BulletinModel(), this);
        presenter.getBulletin();
    }

    @Override
    public int statusColorResId() {
        return R.color.color_ffb900;
    }

    @OnClick(R.id.ib_back)
    void onBackClick(View v){
        finish();
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
    }

    @Override
    public Context currentContext() {
        return this;
    }

    @Override
    public void onSuccess(BulletinBean bulletin) {
        titleBar.setLeftText(bulletin.getTitle());
        webView.loadData(MangoUtils.makeHtml(bulletin.getContent()), "text/html; charset=UTF-8", null);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    protected void onDestroy() {
        if(presenter != null){
            presenter.onDestroy();
        }
        super.onDestroy();
    }
}
