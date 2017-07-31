package cn.com.mangopi.android.ui.activity;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;

import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;

import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.widget.web.MangoWebChromeClient;
import cn.com.mangopi.android.ui.widget.web.MangoWebChromeListener;
import cn.com.mangopi.android.ui.widget.web.MangoWebView;
import cn.com.mangopi.android.util.BusEvent;

public class WebViewActivity extends BaseActivity implements MangoWebChromeListener {

    @Bind(R.id.web_view)
    MangoWebView webView;
    @Bind(R.id.progress)
    ProgressBar progress;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Bus.getDefault().register(this);

        url = getIntent().getStringExtra(Constants.BUNDLE_WEBVIEW_URL);
        if(url != null) {
            if (!url.startsWith("http")) {
                url = "http://".concat(url);
            }
        }

        ClipDrawable drawable = new ClipDrawable(new ColorDrawable(getResources().getColor(R.color.color_ffb900)), Gravity.LEFT, ClipDrawable.HORIZONTAL);
        progress.setProgressDrawable(drawable);

        webView.setWebChromeClient(new MangoWebChromeClient(this));
        //webView.setWebViewClient(new MangoWebViewClient(this));

        webView.loadUrl(url);
    }

    @Override
    protected void onResume() {
        webView.onResume();
        webView.resumeTimers();
        super.onResume();
    }

    @Override
    protected void onPause() {
        webView.onPause();
        webView.pauseTimers();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        webView.stopLoading();
        webView.setVisibility(View.GONE);
        Bus.getDefault().unregister(this);
        super.onDestroy();
    }

    @BusReceiver
    public void onNetEvent(BusEvent.NetEvent event){
        webView.setCacheMode();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    public void back(){
        if(url.equals(webView.getUrl())){
            finish();
        } else if(webView.canGoBack()){
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void onReceivedTitle(String title) {
        //titleBar.setTitle(title == null ? "" : title);
    }

    @Override
    public void onProgressChanged(int newProgress) {
        if(newProgress == 100){
            progress.setVisibility(View.GONE);
        } else {
            if(progress.getVisibility() == View.GONE){
                progress.setVisibility(View.VISIBLE);
            }
            progress.setProgress(newProgress);
        }
    }

    @Override
    public void firstLoadAfter() {
    }
}
