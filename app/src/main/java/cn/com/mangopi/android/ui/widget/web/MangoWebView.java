package cn.com.mangopi.android.ui.widget.web;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import cn.com.mangopi.android.util.NetUtils;

public class MangoWebView extends WebView {

    public MangoWebView(Context context) {
        super(context);
        initSetting();
    }

    public MangoWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSetting();
    }

    public MangoWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSetting();
    }

    public void setCacheMode(){
        if(NetUtils.isNetworkConnected(getContext())) {
            getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }

    private void initSetting(){
        setBackgroundColor(0);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        getSettings().setJavaScriptEnabled(true);
        //getSettings().setUserAgentString("YooYo/1.0 " + getSettings().getUserAgentString());
        getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        setCacheMode();

        getSettings().setDomStorageEnabled(true);
        getSettings().setDatabaseEnabled(true);
        getSettings().setAppCacheEnabled(true);
        String cacheDirPath = getContext().getCacheDir().getAbsolutePath()+"/web_cache";
        getSettings().setDatabasePath(cacheDirPath);
        getSettings().setAppCachePath(cacheDirPath);

        getSettings().setLoadsImagesAutomatically(true);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setPluginState(WebSettings.PluginState.ON);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setAllowFileAccess(true);
        getSettings().setBuiltInZoomControls(true);
        getSettings().setDisplayZoomControls(false);
        getSettings().setUseWideViewPort(true);
        getSettings().setSupportMultipleWindows(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getSettings().setMediaPlaybackRequiresUserGesture(true);
        }

        setLayerType(View.LAYER_TYPE_HARDWARE,  null);

        removeJavascriptInterface("searchBoxJavaBridge_");
        removeJavascriptInterface("accessibility");
        removeJavascriptInterface("accessibilityTraversal");

        clearHistory();
    }

    @Override
    public void goBack() {
        getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        super.goBack();
    }

    @Override
    public void goBackOrForward(int steps) {
        getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        super.goBackOrForward(steps);
    }
}
