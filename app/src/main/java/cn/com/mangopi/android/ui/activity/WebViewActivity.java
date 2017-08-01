package cn.com.mangopi.android.ui.activity;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;

import com.baidu.mobstat.StatService;
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;

import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.widget.web.MangoWebChromeClient;
import cn.com.mangopi.android.ui.widget.web.MangoWebChromeListener;
import cn.com.mangopi.android.ui.widget.web.MangoWebView;
import cn.com.mangopi.android.ui.widget.web.MangoWebViewClient;
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

        //webView.setWebChromeClient(new MangoWebChromeClient(this));
        // 适用于自动埋点版本，用于对webview加载的h5页面进行自动统计；需要在载入页面前调用，建议在webview初始化时刻调用
        // 如果有设置的WebChromeClient，则需要将对象传入，否则影响本身回调；如果没有，第三个参数设置为null即可
        StatService.trackWebView(this, webView, new MangoWebChromeClient(this));
        webView.setWebViewClient(new MangoWebViewClient());

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
        if(progress == null){
            return;
        }
        progress.setProgress(newProgress);
        if(newProgress == 100){
            progress.setVisibility(View.GONE);
        } else {
            if(progress.getVisibility() == View.GONE){
                progress.setVisibility(View.VISIBLE);
            }
        }
    }

}
