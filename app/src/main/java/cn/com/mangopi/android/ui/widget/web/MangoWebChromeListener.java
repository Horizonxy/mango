package cn.com.mangopi.android.ui.widget.web;

public interface MangoWebChromeListener {
    void onReceivedTitle(String title);
    void onProgressChanged(int newProgress);
    void firstLoadAfter();
}
