package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;

import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ContentDetailBean;
import cn.com.mangopi.android.model.data.ContentModel;
import cn.com.mangopi.android.presenter.ContentPresenter;
import cn.com.mangopi.android.ui.viewlistener.ContentDetailListener;
import cn.com.mangopi.android.ui.widget.web.MangoWebView;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.MangoUtils;

public class ContentDetailActivity extends BaseTitleBarActivity implements ContentDetailListener {

    @Bind(R.id.web_view)
    MangoWebView webView;
    long id;
    ContentPresenter contentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);

        id = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);

        contentPresenter = new ContentPresenter(new ContentModel(), this);
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
    public void onSuccess(ContentDetailBean contentDetail) {
        titleBar.setTitle(contentDetail.getTitle());
        webView.loadData(MangoUtils.makeHtml(contentDetail.getContent()), "text/html; charset=UTF-8", null);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    protected void onDestroy() {
        if(contentPresenter !=  null){
            contentPresenter.onDestroy();
        }
        super.onDestroy();
    }
}
