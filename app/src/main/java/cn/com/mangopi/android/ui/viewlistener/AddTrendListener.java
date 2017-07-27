package cn.com.mangopi.android.ui.viewlistener;

import java.util.List;

public interface AddTrendListener extends BaseViewListener {

    void onSuccess();
    String getContent();
    List<String> getPics();
}
